import java.util.*;

public class Level {

    public static class Element {
        int level;
        Node node;

        Element(Node node, int level) {
            this.level = level;
            this.node = node;
        }
    }

    //层序遍历，知道层数
    public static List<List<Integer>> levelTraversal(Node root) {
        List<List<Integer>> retList = new ArrayList<>();
        if (root == null) {
            return retList;
        }
        Queue<Element> queue = new LinkedList<>();
        Element e = new Element(root, 0);
        e.node = root;
        e.level = 0;
        queue.add(e);

        while (!queue.isEmpty()) {
            Element front = queue.poll();//取出队首元素

            //如果层次与长度相等，建立一个新的线性表{}
            if (front.level == retList.size()) {
                retList.add(new ArrayList<>());
            }
            //得到一个位置做尾插
            retList.get(front.level).add(front.node.value);

            //element类型，包含node和value
            if (front.node.left != null) {
                Element l = new Element(front.node.left, front.level + 1);
                l.node = front.node.left;
                l.level = front.level + 1;
                queue.add(l);
            }

            if (front.node.right != null) {
                Element l = new Element(front.node.right, front.level + 1);
                l.node = front.node.right;
                l.level = front.level + 1;
                queue.add(l);
            }
        }
        return retList;
    }

    //判断是否完全二叉树
    //带空层序遍历，遇到null之后是都还有not null出现
    //若有，不是完全二叉树、否则，是完全二叉树
    //层序遍历树，直到遇到了null；检查队列中是否还有not null存在
    public static boolean isCompleteTree(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        //队列为空之前，必然会碰到一个null,所以先不用判空
        //层序遍历，直到遇到第一个null
        while (true) {
            Node front = queue.poll();
            if (front == null) {
                break;
            }
            queue.add(front.left);
            queue.add(front.right);
        }
        //判断剩余元素是否都是null
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node != null) {
                return false;
            }
        }
        return true;
    }

    //层序遍历，非递归
    //前序遍历
    public static void preorderNoR(Node root) {
        Stack<Node> stack = new Stack<>();
        Node cur = root;

        while (!stack.empty() || cur != null) {
            while (cur != null) {
                System.out.println(cur.value);//<第一次经过时打印，即第一次遍历时打印>
                stack.push(cur);
                cur = cur.left;
            }

            //pop出掉栈并取出
            Node top = stack.pop();
            cur = top.right;
        }
    }

    //中序遍历
    public static void inorderNoR(Node root) {
        Stack<Node> stack = new Stack<>();
        Node cur = root;

        while (!stack.empty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            //pop出掉栈并取出
            Node top = stack.pop();
            System.out.println(top.value);//<第二次经过时打印，即出栈时打印>
            cur = top.right;
        }
    }

    //后序遍历
    public static void postorderNoR(Node root) {
        Stack<Node> stack = new Stack<>();
        Node cur = root;
        Node last = null; //上一次被三次完整经过的结点
        while (!stack.empty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            Node top = stack.peek();//取栈顶元素，不删除
            if (top.right == null || top.right == last) {
                stack.pop();//取栈顶元素，删除
                System.out.println(top.value);
                last = top;
            } else {
                cur = top.right;
            }
        }
    }

    //last记录刚刚的元素
    public static void main(String[] args) {
        Node n1 = new Node(1);
        n1.left = new Node(2);
        n1.right = new Node(3);
        n1.left.left = new Node(4);
        List<List<Integer>> list = levelTraversal(n1);
        System.out.println(list);
        isCompleteTree(n1);
        System.out.println("========");
        preorderNoR(n1);
        System.out.println("========");
        inorderNoR(n1);
        System.out.println("========");
        postorderNoR(n1);
    }
}
