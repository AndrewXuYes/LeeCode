package Advanced;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class LRUMap<K, V> {


    public final Map<K, V> cacheMap = new HashMap<>();
    public int cacheSize;
    public int nodeCount;
    public Node<K, V> header;
    public Node<K, V> tailer;

    public LRUMap(int cacheSize) {
        this.cacheSize = cacheSize;
        //头结点的下一个结点为空
        header = new Node<>();
        header.next = null;
        //尾结点的上一个结点为空
        tailer = new Node<>();
        tailer.tail = null;
        //双向链表 头结点的上结点指向尾结点
        header.tail = tailer;
        //尾结点的下结点指向头结点
        tailer.next = header;
    }

    public void put(K key, V value) {
        cacheMap.put(key, value);
        //双向链表中添加结点
        addNode(key, value);
    }

    public V get(K key) {
        Node<K, V> node = getNode(key);

        //移动到头结点
        assert node != null;
        moveToHead(node);
        return cacheMap.get(key);
    }

    public void moveToHead(Node<K, V> node) {
        //如果是最后的一个节点
        if (node.tail == null) {
            node.next.tail = null;
            tailer = node.next;
            nodeCount--;
        }
        //如果是本来就是头节点 不作处理
        if (node.next == null) {
            return;
        }
        //如果处于中间节点
        if (node.tail != null) {
            //它的上一节点指向它的下一节点 也就删除当前节点
            node.tail.next = node.next;
            nodeCount--;
        }
        //最后在头部增加当前节点
        //注意这里需要重新 new 一个对象，不然原本的node 还有着下面的引用，会造成内存溢出。
        node = new Node<>(node.getKey(), node.getValue());
        addHead(node);
    }

    /**
     * 链表查询 效率较低
     */
    public Node<K, V> getNode(K key) {
        Node<K, V> node = tailer;
        while (node != null) {
            if (node.getKey().equals(key)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    /**
     * 写入头结点
     */
    public void addNode(K key, V value) {
        Node<K, V> node = new Node<>(key, value);
        //容量满了删除最后一个
        if (cacheSize == nodeCount) {
            //删除尾结点
            delTail();
        }
        //写入头结点
        addHead(node);
    }

    /**
     * 添加头结点
     */
    public void addHead(Node<K, V> node) {
        //写入头结点
        header.next = node;
        node.tail = header;
        header = node;
        nodeCount++;

        //如果写入的数据大于2个 就将初始化的头尾结点删除
        if (nodeCount == 2) {
            tailer.next.next.tail = null;
            tailer = tailer.next.next;
        }
    }

    public void delTail() {
        //把尾结点从缓存中删除
        cacheMap.remove(tailer.getKey());
        //删除尾结点
        tailer.next.tail = null;
        tailer = tailer.next;

        nodeCount--;
    }

    @Data
    public static class Node<K, V> {
        public K key;
        public V value;
        Node<K, V> tail;
        Node<K, V> next;

        Node() {
        }

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }


    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<K, V> node = tailer;
        while (node != null) {
            sb.append(node.getKey()).append(":")
                    .append(node.getValue())
                    .append("-->");

            node = node.next;
        }
        return sb.toString();
    }

}