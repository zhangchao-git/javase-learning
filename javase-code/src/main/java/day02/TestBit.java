package day02;

//λ�������<<  >>   >>>  | & ~ ^
class TestBit {
    public static void main(String[] args) {
        int i1 = 31;

        System.out.println(i1 << 3);//248
        System.out.println(i1 << 28);

        System.out.println(i1 >> 2);//7
        System.out.println(i1 >>> 2);//7

        int i2 = -31;
        System.out.println(i2 >> 2);//-8
        System.out.println(i2 >>> 2);//1073741816

        System.out.println(12 & 5);//4
        System.out.println(12 | 5);//13
        System.out.println(12 ^ 5);//9
        System.out.println(~12);//-13

    }
}
