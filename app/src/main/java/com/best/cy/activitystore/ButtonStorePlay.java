package com.best.cy.activitystore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ButtonStorePlay {
    public int x, y;
    public int yy;
    public int w, h;

    public Bitmap button_img;

    private Bitmap btn_image[] = new Bitmap[2];

    public int whichPic;

    public ButtonStorePlay(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.whichPic = z;
        int i;

        for (i = 0; i < 2; i++) {
            btn_image[i] = BitmapFactory.decodeResource(StoryPlay.mContext.getResources(), R.drawable.watch00 + whichPic * 2 + i);

            int xWidth = StoryPlay.Width / 11;
            int yWidth = xWidth;

            //서브메뉴 : 상점놀이(손님용), 상점놀이(주인용), 큰수학습하기
            if (whichPic == 2 || whichPic == 3 || whichPic == 4 ) {
                xWidth = StoryPlay.Width / 5;
                yWidth = xWidth; //   studyView8sumodel.Height / 6;
                btn_image[i] = Bitmap.createScaledBitmap(btn_image[i], xWidth, yWidth, true);
            }

            //여자캐릭터 소피아
            if (whichPic == 5) {
                xWidth = StoryPlay.Width / 10;
                yWidth = xWidth;
                btn_image[i] = Bitmap.createScaledBitmap(btn_image[i], xWidth, yWidth, true);
            }

            // 만원, 천원, 백원, 증가 감소 버튼
            if (whichPic == 8 || whichPic == 9) {
                xWidth = StoryPlay.Width / 13;
                yWidth = xWidth;
                btn_image[i] = Bitmap.createScaledBitmap(btn_image[i], xWidth, yWidth, true);
            }

            //물건들(풀, 가위, 지우개, 노트) 버튼
            if (whichPic == 19 || whichPic == 20 || whichPic == 21 || whichPic == 22) {
                xWidth = StoryPlay.Width / 8;
                yWidth = StoryPlay.Width / 6;
                btn_image[i] = Bitmap.createScaledBitmap(btn_image[i], xWidth, yWidth, true);
            }

            //14: 나의 성적보기 버튼
            //6: 다음문제 버튼
            if (whichPic == 14  ||  whichPic == 6) {
                xWidth = StoryPlay.Width / 10;
                yWidth = xWidth;
                btn_image[i] = Bitmap.createScaledBitmap(btn_image[i], xWidth, yWidth, true);
            }

            // 닫기 버튼
            if (whichPic == 11) {
                xWidth = StoryPlay.Width / 6;
                yWidth = StoryPlay.Height / 8;
                btn_image[i] = Bitmap.createScaledBitmap(btn_image[i], xWidth, yWidth, true);
            }

            //스피커 아이콘
            if (whichPic == 12 || whichPic == 13 ) {
                xWidth = StoryPlay.Width / 10;
                yWidth = xWidth;
                btn_image[i] = Bitmap.createScaledBitmap(btn_image[i], xWidth, yWidth, true);
            }


            // 0: 다음문제, 1: 정답확인, 7: 나가기버튼,
            // 10: 큰수 읽기 버튼
            // 15: 0으로 설정 버튼 - 물건개수 초기화 버튼
            if (whichPic == 0 || whichPic == 1 || whichPic == 7 || whichPic == 15 || whichPic==10) {
                xWidth = StoryPlay.Width / 10;
                yWidth = xWidth;
                btn_image[i] = Bitmap.createScaledBitmap(btn_image[i], xWidth, yWidth, true);
            }

            //닫기버튼
            if (whichPic == 11) {
                xWidth = StoryPlay.Width / 9;
                yWidth = StoryPlay.Height / 8;
                btn_image[i] = Bitmap.createScaledBitmap(btn_image[i], xWidth, yWidth, true);
            }

            w = btn_image[0].getWidth() / 2;
            h = btn_image[0].getHeight() / 2;
            button_img = btn_image[0];
        }
    }

    ButtonStorePlay(int x, int y, int z, int p){
        this.x = x;
        this.y = y;
        this.whichPic = z;
        int i;
        for (i = 0; i < 2; i++) {
            btn_image[i] = BitmapFactory.decodeResource(StoryPlay.mContext.getResources(), R.drawable.bignumber00 + whichPic * 2 + i);

            int xWidth;
            int yWidth;

            //서브메뉴 : 1~50문제풀기, 수모형 연습하기,  수모형 문제풀기, 수모형 덧셈
            if (whichPic == 0 || whichPic == 1 || whichPic == 2 || whichPic == 3 || whichPic == 4 || whichPic == 5 || whichPic == 6) {
                xWidth = StoryPlay.Width / 11;
                yWidth = xWidth; //   studyView8sumodel.Height / 6;
                btn_image[i] = Bitmap.createScaledBitmap(btn_image[i], xWidth, yWidth, true);
            }

            w = btn_image[0].getWidth() / 2;
            h = btn_image[0].getHeight() / 2;
            button_img = btn_image[0];

        }
    }


    public void showOriginalImage() {
        button_img = btn_image[0];
    }

    public void showPressedImage() {
        button_img = btn_image[1];
    }

    public void btn_exit() {
        System.exit(0);
    }
}
