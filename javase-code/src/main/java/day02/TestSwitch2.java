package day02;

class TestSwitch2 {
    public static void main(String[] args) {

        String season = "SPRING";

        switch (season) {
            case "SPRING":
                System.out.println("春天");
                break;
            case "SUMMER":
                System.out.println("夏天");
                break;
            case "AUTUMN":
                System.out.println("秋天");
                break;
            case "WINTER":
                System.out.println("冬天");
                break;
            default:
                System.out.println("输入有误");
                break;

        }


    }
}
