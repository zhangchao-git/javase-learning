package day03;

/**
 * break:ʹ����switch-case�л���ѭ����
 * ���ʹ����ѭ���У���ʾ������"��ǰ"ѭ��
 * <p>
 * continue:ʹ����ѭ���ṹ��
 * ��ʾ������"����"ѭ��
 * <p>
 * ����break��continue�б�ǩ��ʹ�á�����⣩
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
                    //������ǰ���������ѭ���������=============
//                    break;
                    //�������α���ѭ���������=============
//                    continue;
                    //������ǰѭ�������Խ���Ƕ��ѭ�����������=============
//                    break label;
                    //��������ѭ�������Խ���Ƕ��ѭ�����������=============
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
                //��break��continue���֮�������������䣬ӦΪ��ԶҲ�����ܱ�ִ�У�
//				System.out.println("��־������ҪԼ��");
            }

            System.out.println(i);
        }
    }
}
