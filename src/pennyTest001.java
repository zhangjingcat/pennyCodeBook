import java.security.PrivateKey;
import java.util.*;
import java.util.regex.Pattern;
//import static org.junit.Assert.assertEquals;


public class pennyTest001 {

    // 私有成员变量 并查集对象
    private UnionFind uf = new UnionFind(99999999);

    // 私有类 并查集
    private class UnionFind {
        private int[] parent;
        private int[] rank;
        private int count;
        // 是否成环
        private boolean hasCycle = false;

        UnionFind(int n) {
            this.parent = new int[n];
            this.rank = new int[n];
            this.count = n;
            // 初始化并查集的时候 自己指向自己 秩均为0
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        // 路径压缩
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        // 按秩合并
        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) {
                this.hasCycle = true;
                return true;
            }
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootY] < rank[rootX]) {
                parent[rootY] = rootX;
            } else {
                parent[rootX] = rootY;
                rank[rootY]++;
            }
            // 每一次成功合并 联通分量数目减去1
            count--;
            return false;
        }

        // 联通分量数目 本题暂时用不到
        public int getCount() {
            return this.count;
        }

        // 根据是否成环返回结果
        public String returnMsg() {
            if (!this.hasCycle) {
                return "valid input without circle";
            } else {
                return "invalid input with circle";
            }
        }

    }

    /**
     * 将四位EXCEL行列编码转换成整型数字
     *
     * @param validString
     * @return
     */
    public int convertStrToInt(String validString) {
        char[] charArray = validString.toCharArray();
        int ans = 0;
        for(int i=0; i < 4;i++) {
            ans += (charArray[i] - '0') * Math.pow(10,4-i);
        }
        return ans;
    }


    /**
     * 前处理+合并
     *
     * @param inputString
     */
    public void splitStringAndUnion(String inputString) {
        String regex01 = " = ";
        String[] data = inputString.split(regex01);
        String father = data[0];
        int fatherInt = convertStrToInt(father);
        String children = data[1];
        char[] childArray = children.toCharArray();
        int i = 0;
        // i充当截断指针 寻找到具体满足要求的第一个字母即可截断四位拿走
        while(i < childArray.length) {
            char childChar = childArray[i];
            if (Character.isLetter(childChar)) {
                String childString = children.substring(i, i+4);
                i = i+4;
                int childInt = convertStrToInt(childString);
                // 结果单元格 和 计算公式单元格中每一个元素建立连接
                boolean cycleResult = uf.union(fatherInt, childInt);
                // 确认有环就无须重复合并，直接退出循环
                if (cycleResult) {
                    break;
                }
            } else {
                i = i+1;
        }
        }
    }

    /**
     * 具体主函数入口
     *
     * @param inputStringList
     * @return
     */
    public String testExcel(String[] inputStringList) {
        // process raw data
        for(int i=0; i< inputStringList.length; i++) {
            splitStringAndUnion(inputStringList[i]);
        }
        System.out.println(uf.returnMsg());
        return uf.returnMsg();
    }

    public static void main(String[] args) {
//        System.out.println("AA00 int" + new pennyTest001().convertStrToInt("AA00"));
//        System.out.println("AA01 int" + new pennyTest001().convertStrToInt("AA01"));
//        System.out.println("AB00 int" + new pennyTest001().convertStrToInt("AB00"));
//        System.out.println("AB01 int" + new pennyTest001().convertStrToInt("AB01"));

        // 辅助功能单元测试
//        Set<String> stringSet = new HashSet<>();
//        Set<Integer> intSet = new HashSet<>();
//        for(char x= 'A';x<'Z';x++) {
//            char tempX = x;
//            for(char y = 'A';y<'Z';y++) {
//                char tempY = y;
//                for(char z = '0';z<'9';z++) {
//                    char tempZ = z;
//                    for(char k = '0';k<'9';k++) {
//                        char tempK = k;
//                        stringSet.add(new char[]{tempX,tempY,tempZ,tempK}.toString());
//                    }
//                }
//            }
//        }
//        for(String s: stringSet) {
//            intSet.add(new pennyTest001().convertStrToInt(s));
//        }
//        System.out.println("stringSet.size()" + stringSet.size());
//        System.out.println("intSet.size()" + intSet.size());
        // 断言确认没有重复转换
//        assertEquals(stringSet.size(), intSet.size());

        // 主函数单元测试
        String res001 = new pennyTest001().testExcel(new String[]{"AA00 = 10", "AA01 = AA00 + AB00", "AB00 = 15"});
        // 断言确认无环
//        assertEquals(res001, "valid input without circle");

        String res002 = new pennyTest001().testExcel(new String[]{"AA00 = 10", "AB00 = (AA00 + AA01)*15", "AA01 = 20 + AB00"});
        // 断言确认有环
//        assertEquals(res002, "invalid input with circle");

    }
}
