package day03;

/**
 * break:使用在switch-case中或者循环中
 * 如果使用在循环中，表示：结束"当前"循环
 * <p>
 * continue:使用在循环结构中
 * 表示：结束"当次"循环
 * <p>
 * 关于break和continue中标签的使用。（理解）
 */
class TestBreakContinue {
    public static void main(String[] args) {
		/*
		for(int i = 1;i <= 10;i++){
			if(i % 4 == 0){
				//break;
				continue;
			}

			System.out.println(i);
		}
		*/
        label:
        for (int i = 1; i < 5; i++) {
            System.out.println("iiiii=" + i);
            for (int j = 1; j <= 10; j++) {
                if (j % 4 == 0) {
                    //结束当前挨着最近的循环，会输出=============
//                    break;
                    //结束当次本次循环，会输出=============
//                    continue;
                    //结束当前循环，可以结束嵌套循环，不会输出=============
//                    break label;
                    //结束当次循环，可以结束嵌套循环，不会输出=============
//                    continue label;
                }
                System.out.println("jjjjjjj=" + j);
            }
            System.out.println("=============");
        }

        System.out.println();
        for (int i = 1; i <= 10; i++) {
            if (i % 4 == 0) {
                break;
                //continue;
                //在break和continue语句之后不能添加其他语句，应为永远也不可能被执行！
//				System.out.println("林志玲晚上要约我");
            }

            System.out.println(i);
        }
    }
}
