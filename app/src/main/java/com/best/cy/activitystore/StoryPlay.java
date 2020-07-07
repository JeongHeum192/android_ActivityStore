package com.best.cy.activitystore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StoryPlay extends View {

    int unitDistance;  //화면배치에 사용할 단위 크기. 여기서는 가로크기의 26분의 1이다.

    //문제를 푼 후 answerValue 값이 1이면 맞았다는 이미지(O)가 나옴.
    //문제를 맞추면 answerValue 값이 1 이 되고, 문제를 틀리면 값이 2 가된다.
    // 값이 0 이면 이미지가 안 나옴.
    int answerValue = 0;
    static int soundOk = 1;

    static Context mContext;

    int oNumber = 0; // 맞은 개수
    int xNumber = 0; // 틀린 개수
    String toNumber = ""; //전체 맞은 개수(누적된 통계)
    String txNumber = ""; //전체 틀린 개수(누적된 통계)

    //풀 버튼을 눌렀을 경우 풀버튼 이미지가 바뀌는데 사용
    int btn_pressed1=0;
    int delayCount1=0;

    //가위 버튼을 눌렀을 경우 풀버튼 이미지가 바뀌는데 사용
    int btn_pressed2=0;
    int delayCount2=0;

    //지우개 버튼을 눌렀을 경우 풀버튼 이미지가 바뀌는데 사용
    int btn_pressed3=0;
    int delayCount3=0;

    //노트 버튼을 눌렀을 경우 풀버튼 이미지가 바뀌는데 사용
    int btn_pressed4=0;
    int delayCount4=0;

    //0으로 설정 버튼을 눌렀을 경우 버튼 이미지가 바뀌는데 사용
    int btn_pressed5=0;
    int delayCount5=0;

    int btn_manplus_press=0;
    int delay_manplus_count=0;

    int btn_manminus_press=0;
    int delay_manminus_count=0;

    int btn_chunplus_press=0;
    int delay_chunplus_count=0;

    int btn_chunminus_press=0;
    int delay_chunminus_count=0;

    int btn_bakplus_press=0;
    int delay_bakplus_count=0;

    int btn_bakminus_press=0;
    int delay_bakminus_count=0;


    //상점놀이(주인용)에서 다음문제 버튼을 눌렀을 경우 버튼 이미지가 바뀌는데 사용
    int btn_pressed6=0;
    int delayCount6=0;

    //물건이 터치되면 값이 1이 됨.
    //손님이 2개 이상의 물건을 구입해야 하기 때문에 (문제에서 물건의 종류를 2개 이상 구입하도록 함)
    //이를 체크하기 위해서 사용
    int numThing1;  // 풀을 1개 이상 선택하면 값이 1이 된다.
    int numThing2;  // 가위를 1개 이상 선택하면 값이 1이 된다.
    int numThing3;  // 지우개를 1개 이상 선택하면 값이 1이 된다.
    int numThing4;  // 노트를 1개 이상 선택하면 값이 1이 된다.

    static int submenuOk = 1;

    int selectionMenu = 3;   //selectionMenu == 3 이면 손님용, selectionMenu = 4 이면 주인용

    //makingQestion값이 1 이면 랜덤으로 손님이 구입한 물건의 갯수를 만들게 된다.
    // 주인용에서 구입한 물건을 랜덤으로 만들고 다음문제 터치전까지 유지하기 위해 필요
    int makingQestion = 0;

    //물건 개수를 저장하는 변수
    int thing1;   //풀의 개수를 저장
    int thing2;   //가위 개수를 저장
    int thing3;   //지우개 개수를 저장
    int thing4;   //노트 개수를 저장
    int payMoney; //물건값
    int userMoney; //사용자가 지불하는 돈

    Paint paint = new Paint(); //내 정보보기에 나오는 글자 크기
    Paint paint2 = new Paint(); //작은 글씨 크기(맞은 개수, 틀린 개수)-하얀색 글씨
    Paint paint21 = new Paint(); //중간 크기 글씨 크기(맞은 개수, 틀린 개수)-하얀색 글씨
    Paint paint22 = new Paint(); //작은 글씨 크기(맞은 개수, 틀린 개수)-빨간색 글씨
    Paint paint3 = new Paint(); //대부분 글자의 크기에 사용
    Paint paint4 = new Paint(); //제일 작은 글씨 크기(구입한 물건 개수)
    Paint paint5 = new Paint(); //제일 작은 흰색 글씨
    Paint paint6 = new Paint(); //빨간색 글씨 : 합계를 나타낼 때 사용
    Paint paint7 = new Paint(); //노란색 글씨 : 사용설명서 제목으로 사용
    Paint paint8 = new Paint(); //타이틀 글씨
    Paint paint9 = new Paint();  //학습목표 글씨
    Paint paint10 = new Paint();
    Paint paint11 = new Paint(); //큰 수 학습하기: 숫자를 한글로 읽어줄 때 사용
    Paint paint12 = new Paint(); //aplha 적용, 큰 수 학습하기 에서 선택되지 않은 버튼은 알파값을 적용함.
    Paint paint13 = new Paint();  //큰수 읽기 - 큰수 읽는 방법에서 제목 배경으로 사용(직사각형구현)

    ButtonStorePlay btnSoundOn;
    ButtonStorePlay btnSoundOff;
    ButtonStorePlay btnSetting;  //큰 수 읽는 방법 버튼
    ButtonStorePlay btnExit;

    ButtonStorePlay btnMenu1;  //손님용 가게놀이 메뉴
    ButtonStorePlay btnMenu2;  //주인용 가게놀이 메뉴
    ButtonStorePlay btnMenu3;  //큰수 학습하기 메뉴

    ButtonStorePlay btnMyInfo;
    ButtonStorePlay btnMyInfoClose;

    ButtonStorePlay btnNextQue;
    ButtonStorePlay btnCheckAns;

    ButtonStorePlay btnAdd10000; // 10000원 1개 증가 버튼
    ButtonStorePlay btnMinus10000;  // 10000원 1개 감소 버튼
    ButtonStorePlay btnAdd1000;  // 1000원 1개 증가 버튼
    ButtonStorePlay btnMinus1000;  // 1000원 1개 감소 버튼
    ButtonStorePlay btnAdd100;   // 100원 1개 증가 버튼
    ButtonStorePlay btnMinus100;  // 100원 1개 감소 버튼

    //만, 십만, 백만, 천만,  등 선택버튼
    ButtonStorePlay bigNumber1;
    ButtonStorePlay bigNumber2;
    ButtonStorePlay bigNumber3;

    ButtonStorePlay bigNumber4;
    ButtonStorePlay bigNumber5;
    //여자 캐릭터
    ButtonStorePlay sophia;

    static int Width, Height;
    Bitmap answerx;
    Bitmap answero;

    Bitmap manWon;
    int manWon_Width;
    int manWon_Height;
    Bitmap cheonWon;
    Bitmap baekWon;

    Bitmap blueScreen;
    Bitmap backGround; //상점 뒷 배경(for guest)
    Bitmap backGround2; //상점 뒷 배경(for owner)
    Bitmap backGround3; //큰 수 학습 뒷 배경
    Bitmap greenBoard; //문제 풀이 과정이 나오는 칠판
    Bitmap greencoin;


    int explainOk = 0; //정답확인 버튼을 클릭하면 1이 되며 문제풀이과정이 나옴
    int howToPlay = 1;  // 이값이 1 이면 가게놀이(손님용) 사용설명서 화면이 제시된다.
    int howToPlay2 = 1;  // 이값이 1 이면 가게놀이(가게용) 사용설명서 화면이 제시된다

    ButtonStorePlay thingGlue;  // 풀 버튼
    ButtonStorePlay thingScissors;  //가위 버튼
    ButtonStorePlay thingEraser;  //지우개 버튼
    ButtonStorePlay thingBook;  //책 버튼
    ButtonStorePlay thingNumInit; // 손님용 가게놀이에서 값들을 0으로 초기화하는 버튼
    ButtonStorePlay btnNextNumber; //큰 수학습에서 사용되는 [다음문제]버튼

    int userManwon, userCheonwon, userBaekwon;  //userManwon 만원   userCheonwon 천원  userBaekwon 백원

    //myInfo 값이 1이면 사용자의 맞은 개수, 틀린 개수를 확인할 수 있다.
    int myInfo = 0;

    Bitmap explain1;// 큰 수 읽는 방법 설명서 1
    Bitmap explain2;// 큰 수 읽는 방법 설명서 2
    Bitmap explain3;// 큰 수 읽는 방법 설명서 3
    Bitmap explain4;// 큰 수 읽는 방법 설명서 4
    Bitmap explain5;// 큰 수 읽는 방법 설명서 5

    static int dingdongdaeng, taeng;

    final static SoundPool sdPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
    ;

    public StoryPlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setFocusable(true);
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        Display display = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Width = display.getWidth();
        Height = display.getHeight();
        unitDistance = Width / 26;

        btnExit = new ButtonStorePlay(Width - unitDistance * 3 - unitDistance / 2, unitDistance - unitDistance / 3, 7); // 나가기 exit

        btnSoundOn = new ButtonStorePlay(Width - unitDistance * 10 + unitDistance / 4, btnExit.y, 12);
        btnSoundOff = new ButtonStorePlay(Width - unitDistance * 10 + unitDistance / 4, btnExit.y, 13);

        btnSetting = new ButtonStorePlay(btnExit.x - btnExit.w * 2 - btnExit.w / 5, unitDistance - unitDistance / 3, 10); //큰 수 읽는 방법 버튼

        // 서브메뉴 첫번째 메뉴 - 상점놀이(손님)
        btnMenu1 = new ButtonStorePlay(Width / 27, Height * 2 / 3 - unitDistance * 2, 2); // 서브메뉴 첫번째 메뉴 - 상점놀이(손님)
        // 서브메뉴 두번째 메뉴 - 상점놀이(주인)
        btnMenu2 = new ButtonStorePlay(btnMenu1.x + btnMenu1.w / 4 + btnMenu1.w * 2, btnMenu1.y, 3);
        //서브메뉴 3번째 - 큰수 학습하기
        btnMenu3 = new ButtonStorePlay(btnMenu2.x + btnMenu2.w / 4 + btnMenu2.w * 2, btnMenu2.y, 4);

        //여자 캐릭터 소피아
        sophia = new ButtonStorePlay(Width/20, Height /15, 5);

        btnMyInfo = new ButtonStorePlay(Width - btnExit.w * 5, btnExit.y, 14);  //my inFo 성적보기
        btnMyInfoClose = new ButtonStorePlay(Width * 6 / 7, Height - unitDistance * 2 - unitDistance / 2, 11); //my Info 닫기 버튼

        manWon = BitmapFactory.decodeResource(this.getResources(), R.drawable.watch36);  //만원
        manWon = Bitmap.createScaledBitmap(manWon, Width * 3 / 20, Width / 14, true);
        manWon_Width = manWon.getWidth();
        manWon_Height = manWon.getWidth();

        cheonWon = BitmapFactory.decodeResource(this.getResources(), R.drawable.watch34);  //천원
        cheonWon = Bitmap.createScaledBitmap(cheonWon, Width * 3 / 20, Width / 14, true);

        baekWon = BitmapFactory.decodeResource(this.getResources(), R.drawable.watch32);  //백원
        baekWon = Bitmap.createScaledBitmap(baekWon, Width / 20, Width / 30, true);

        greencoin = BitmapFactory.decodeResource(this.getResources(), R.drawable.greencoin);
        greencoin = Bitmap.createScaledBitmap(greencoin, Width / 12, Width / 12, true);

        explain1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.explain1);
        explain1 = Bitmap.createScaledBitmap(explain1, Width - unitDistance * 13, Height / 6, true);

        explain2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.explain2);
        explain2 = Bitmap.createScaledBitmap(explain2, Width - unitDistance * 13, Height / 6, true);

        explain3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.explain3);
        explain3 = Bitmap.createScaledBitmap(explain3, Width - unitDistance * 13, Height / 6, true);

        explain4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.explain4);
        explain4 = Bitmap.createScaledBitmap(explain4, Width - unitDistance * 14, Height / 4 + unitDistance / 2, true);

        explain5 = BitmapFactory.decodeResource(this.getResources(), R.drawable.explain5);
        explain5 = Bitmap.createScaledBitmap(explain5, Width - unitDistance * 16, Height / 6, true);

        btnNextQue = new ButtonStorePlay(btnExit.x, btnExit.h * 3, 0);  // 다음문제
        btnCheckAns = new ButtonStorePlay(btnExit.x, btnExit.h * 6 - btnExit.h / 4, 1); //정답확인

        btnMinus10000 = new ButtonStorePlay(unitDistance + unitDistance / 2, Height - unitDistance * 3 + unitDistance / 2, 9);    //만원 감소
        btnAdd10000 = new ButtonStorePlay(btnMinus10000.x + unitDistance * 3, btnMinus10000.y, 8);    //만원 증가

        btnMinus1000 = new ButtonStorePlay(btnAdd10000.x + unitDistance * 5, btnMinus10000.y, 9);     //십단위 1 증가
        btnAdd1000 = new ButtonStorePlay(btnMinus1000.x + unitDistance * 3, btnMinus10000.y, 8);     //십단위 1감소

        btnMinus100 = new ButtonStorePlay(btnAdd1000.x + unitDistance * 5, btnMinus10000.y, 9);     //일단위 1 증가
        btnAdd100 = new ButtonStorePlay(btnMinus100.x + unitDistance * 3, btnMinus10000.y, 8);     //일단위 1감소

        //상점에서 사고 파는 물건들
        thingGlue = new ButtonStorePlay(unitDistance / 2, unitDistance * 3, 19);
        thingScissors = new ButtonStorePlay(thingGlue.x + thingGlue.w * 2, thingGlue.y, 20);
        thingEraser = new ButtonStorePlay(thingScissors.x + thingScissors.w * 2, thingGlue.y, 21);
        thingBook = new ButtonStorePlay(thingEraser.x + thingEraser.w * 2, thingGlue.y, 22);
        thingNumInit = new ButtonStorePlay(btnExit.x, btnExit.h * 3, 15); //물건 개수 초기화 버튼

        btnNextNumber = new ButtonStorePlay(btnExit.x, btnExit.h * 3, 6); //큰수 학습하기에 사용되는 다음문제 버튼

        bigNumber1 = new ButtonStorePlay(thingGlue.x, Height * 4 / 5, 0, 0);
        bigNumber2 = new ButtonStorePlay(thingGlue.x + unitDistance * 3, Height * 4 / 5, 1, 0);
        bigNumber3 = new ButtonStorePlay(thingGlue.x + unitDistance * 6, Height * 4 / 5, 2, 0);
        bigNumber4 = new ButtonStorePlay(thingGlue.x + unitDistance * 9, Height * 4 / 5, 3, 0);
        bigNumber5 = new ButtonStorePlay(thingGlue.x + unitDistance * 12, Height * 4 / 5, 4, 0);

        answerx = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.answerx);
        int mWidth = Width / 6;
        answerx = Bitmap.createScaledBitmap(answerx, mWidth, mWidth, true);
        answero = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.answero);
        answero = Bitmap.createScaledBitmap(answero, mWidth, mWidth, true);

        blueScreen = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.chilpan);
        blueScreen = Bitmap.createScaledBitmap(blueScreen, Width, Height, true);

        backGround = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.background);
        backGround = Bitmap.createScaledBitmap(backGround, Width, Height, true);

        backGround2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.background2);
        backGround2 = Bitmap.createScaledBitmap(backGround2, Width, Height, true);

        backGround3 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.backbignumber);
        backGround3 = Bitmap.createScaledBitmap(backGround3, Width, Height, true);

        greenBoard = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.greenboard);
        greenBoard = Bitmap.createScaledBitmap(greenBoard, Width, Height / 2, true);

        dingdongdaeng = sdPool.load(mContext, R.raw.dingdongdaeng, 1);
        taeng = sdPool.load(mContext, R.raw.taeng, 2);

        loadFile();
        initPaint();
    }

    public void loadFile() {
        try {
            FileInputStream inResult = mContext.openFileInput("mFile.txt");
            byte[] txt = new byte[inResult.available()];
            inResult.read(txt);
            String str = new String(txt);
            str = str.trim();
            String[] tmp = new String[7];
            tmp = str.split("[|]");
            toNumber = tmp[0];
            txNumber = tmp[1];  //   tmp[3] 틀린개수
            inResult.close();
        } catch (IOException e) {
            toNumber = "0";
            txNumber = "0";
        }

        //숫자 읽기
        //makeBigNumber();
    }

    public void initPaint() {
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.create("", Typeface.BOLD));

        paint2.setColor(Color.WHITE);
        paint2.setAntiAlias(true);
        paint2.setTypeface(Typeface.create("", Typeface.BOLD));

        paint21.setColor(Color.WHITE);
        paint21.setAntiAlias(true);
        paint21.setTypeface(Typeface.create("", Typeface.BOLD));

        paint22.setColor(Color.RED);
        paint22.setAntiAlias(true);
        paint22.setTypeface(Typeface.create("", Typeface.BOLD));

        paint5.setColor(Color.WHITE);
        paint5.setAntiAlias(true);
        paint5.setTypeface(Typeface.create("", Typeface.BOLD));

        paint3.setColor(Color.parseColor("#FFFFFF")); //white color
        paint3.setAntiAlias(true);
        paint3.setTypeface(Typeface.create("", Typeface.BOLD));

        paint6.setColor(Color.parseColor("#99004c"));
        paint6.setAntiAlias(true);
        paint6.setTypeface(Typeface.create("", Typeface.BOLD));

        //    paint4.setColor(Color.WHITE);
        paint4.setColor(Color.parseColor("#0054FF")); //white color
        paint4.setAntiAlias(true);
        //  paint4.setTypeface(Typeface.create("", Typeface.BOLD));


        paint10.setColor(Color.parseColor("#08477C"));
        paint10.setAntiAlias(true);
        paint10.setTypeface(Typeface.create("", Typeface.BOLD));

        paint7.setColor(Color.parseColor("#FFFF36"));
        paint7.setAntiAlias(true);
        paint7.setTypeface(Typeface.create("", Typeface.BOLD));

        paint10.setColor(Color.parseColor("#08477C"));
        paint10.setAntiAlias(true);
        paint10.setTypeface(Typeface.create("", Typeface.BOLD));

        paint11.setColor(Color.parseColor("#99004C"));
        paint11.setAntiAlias(true);
        paint11.setTypeface(Typeface.create("", Typeface.BOLD));

        paint12.setAlpha(150);

        paint13.setColor(Color.parseColor("#CCA63D"));

        paint8.setTextSize(Width / 28); //타이틀 글씨
        paint8.setColor(Color.WHITE);
        paint8.setAntiAlias(true);
        paint8.setTypeface(Typeface.create("", Typeface.BOLD));

        paint9.setColor(Color.parseColor("#D4F4FA")); //연한 파란색
        paint9.setAntiAlias(true);
        paint9.setTypeface(Typeface.create("", Typeface.BOLD));
    }

    public void onDraw(Canvas canvas) {

        canvas.drawBitmap(blueScreen,0, 0,null);

        paint.setTextSize(Width / 22); //내정보보기에 나오는 글자 크기
        paint2.setTextSize(Width / 26); //작은 글씨 크기(맞은 개수, 틀린 개수)-하얀색 글씨
        paint21.setTextSize(Width / 28); //중간 크기 글씨 크기(맞은 개수, 틀린 개수)-하얀색 글씨
        paint22.setTextSize(Width / 26); //작은 글씨 크기(맞은 개수, 틀린 개수)-빨간색 글씨
        paint3.setTextSize(Width / 16); //큰 수 읽기에서 제시되는 숫자에 사용-흰색 글씨
        paint4.setTextSize(Width / 24); //제일 작은 글씨 크기(구입한 물건 개수)
        paint5.setTextSize(Width / 30); //제일 작은 흰색 글씨
        paint6.setTextSize(Width / 22); //빨간색 글씨 : 합계를 나타낼 때 사용
        paint7.setTextSize(Width / 22); //노란색 글씨 : 사용설명서 제목으로 사용
        paint8.setTextSize(Width / 25); //타이틀 글씨
        paint9.setTextSize(Width / 23); //학습목표 글씨
        paint11.setTextSize(Width / 23);  //큰 수 학습하기: 숫자를 한글로 읽어줄 때 사용

        if(selectionMenu == 1 || selectionMenu == 2){

            if(btn_pressed1 == 1) {
                delayCount1 += 1;
                if (delayCount1 > 5) {
                    thingGlue.showOriginalImage();
                    delayCount1 = 0;
                    btn_pressed1=0;
                }
            }

            if(btn_pressed2 == 1) {
                delayCount2 += 1;
                if (delayCount2 > 5) {
                    thingScissors.showOriginalImage();
                    delayCount2 = 0;
                    btn_pressed2=0;
                }
            }

            if(btn_pressed3 == 1) {
                delayCount3 += 1;
                if (delayCount3 > 5) {
                    thingEraser.showOriginalImage();
                    delayCount3 = 0;
                    btn_pressed3=0;
                }
            }

            if(btn_pressed4 == 1) {
                delayCount4 += 1;
                if (delayCount4 > 5) {
                    thingBook.showOriginalImage();
                    delayCount4 = 0;
                    btn_pressed4=0;
                }
            }

            if(btn_manplus_press == 1) {
                delay_manplus_count += 1;
                if(delay_manplus_count > 5) {
                    btnAdd10000.showOriginalImage();
                    delay_manplus_count = 0;
                    btn_manplus_press = 0;
                }
            }

            if(btn_manminus_press == 1) {
                delay_manminus_count += 1;
                if(delay_manminus_count > 5) {
                    btnMinus10000.showOriginalImage();
                    delay_manminus_count = 0;
                    btn_manminus_press = 0;
                }
            }

        }

        if(selectionMenu == 1) {

            if (btn_pressed5 == 1) {
                delayCount5 += 1;
                if (delayCount5 > 5) {
                    thingNumInit.showOriginalImage();
                    delayCount5 = 0;
                    btn_pressed5 = 0;
                }
            }
        }

        //손님용 상점
        if (myInfo == 0)
            if (selectionMenu == 1) {

                if(explainOk == 0 && howToPlay ==0) {
                    canvas.drawBitmap(backGround, 0, 0, null);

                    //userManwon baek,  userCheonwon sip, as2 il
                    canvas.drawBitmap(btnCheckAns.button_img, btnCheckAns.x, btnCheckAns.y, null);

                    if (thing1 <= 0) thing1 = 0;
                    if (thing2 <= 0) thing2 = 0;
                    if (thing3 <= 0) thing3 = 0;
                    if (thing4 <= 0) thing4 = 0;

                    payMoney = thing1 * 800 + thing2 * 1000 + thing3 * 500 + thing4 * 700;
                    userMoney = userManwon * 10000 + userCheonwon * 1000 + userBaekwon * 100;
                    canvas.drawText(userMoney + "원", Width / 4 - unitDistance / 3, Height / 2 + unitDistance * 2 + unitDistance / 2, paint);

                    canvas.drawBitmap(thingGlue.button_img, thingGlue.x, thingGlue.y, null);
                    canvas.drawBitmap(thingScissors.button_img, thingScissors.x, thingScissors.y, null);
                    canvas.drawBitmap(thingEraser.button_img, thingEraser.x, thingEraser.y, null);
                    canvas.drawBitmap(thingBook.button_img, thingBook.x, thingBook.y, null);
                    canvas.drawBitmap(thingNumInit.button_img, thingNumInit.x, thingNumInit.y, null);


                    canvas.drawText(thing1 + " 개", thingGlue.x + thingGlue.w / 2, thingGlue.y + thingGlue.h * 2 - thingGlue.h / 8, paint4);
                    canvas.drawText(thing2 + " 개", thingScissors.x + thingGlue.w / 2, thingScissors.y + thingGlue.h * 2 - thingGlue.h / 8, paint4);
                    canvas.drawText(thing3 + " 개", thingEraser.x + thingGlue.w / 2, thingEraser.y + thingGlue.h * 2 - thingGlue.h / 8, paint4);
                    canvas.drawText(thing4 + " 개", thingBook.x + thingGlue.w / 2, thingBook.y + thingGlue.h * 2 - thingGlue.h / 8, paint4);
                    if (userBaekwon >= 9) {
                        userBaekwon = 9;
                    }
                    if (userCheonwon >= 9) {
                        userCheonwon = 9;
                    }

                    if (userManwon >= 9) userManwon = 9;
                    if (userManwon <= 0) userManwon = 0;

                    canvas.drawBitmap(manWon, unitDistance * 2, Height * 3 / 5 + unitDistance + unitDistance / 3, null);  //만원 이미지
                    canvas.drawText(userManwon + "장", unitDistance + manWon_Width + unitDistance + unitDistance / 3, Height * 3 / 5 + unitDistance * 2 + unitDistance / 3, paint2);

                    canvas.drawBitmap(cheonWon, Width / 3 + unitDistance, Height * 3 / 5 + unitDistance + unitDistance / 3, null);
                    canvas.drawText(userCheonwon + "장", Width / 3 + manWon.getWidth() + unitDistance + unitDistance / 3, Height * 3 / 5 + unitDistance * 2 + unitDistance / 3, paint2);

                    canvas.drawBitmap(baekWon, Width * 2 / 3 + unitDistance, Height * 3 / 5 + unitDistance + unitDistance / 2, null);
                    canvas.drawText(userBaekwon + "개", Width * 2 / 3 + unitDistance * 3, Height * 3 / 5 + unitDistance * 2 + unitDistance / 3, paint2);

                    canvas.drawBitmap(btnAdd10000.button_img, btnAdd10000.x, btnAdd10000.y, null);
                    canvas.drawBitmap(btnMinus10000.button_img, btnMinus10000.x, btnMinus10000.y, null);
                    canvas.drawBitmap(btnAdd1000.button_img, btnAdd1000.x, btnAdd1000.y, null);
                    canvas.drawBitmap(btnMinus1000.button_img, btnMinus1000.x, btnMinus1000.y, null);
                    canvas.drawBitmap(btnAdd100.button_img, btnAdd100.x, btnAdd100.y, null);
                    canvas.drawBitmap(btnMinus100.button_img, btnMinus100.x, btnMinus100.y, null);
                }  //상점화면 처리 끝



                // if((thing1 + thing2 + thing3 + thing4)>=2)
                if (explainOk == 1) {

                    canvas.drawBitmap(greenBoard, 0, Height / 2, null);
                    if ((thing1 + thing2 + thing3 + thing4) >= 4 && (numThing1 + numThing2 + numThing3 + numThing4) >= 2) {
                        int sum = 800 * thing1 + 1000 * thing2 + 500 * thing3 + 700 * thing4;

                        canvas.drawText("  연필: " + "800원 x" + thing1 + "개 =" + 800 * thing1 + "원", unitDistance / 2, Height / 2 + unitDistance * 2, paint5);
                        canvas.drawText("  가위: " + "1000원 x" + thing2 + "개 =" + 1000 * thing2 + "원", unitDistance / 2, Height / 2 + unitDistance * 2 + unitDistance, paint5);
                        canvas.drawText("  지우개: " + "500원 x" + thing3 + "개 =" + 500 * thing3 + "원", unitDistance / 2, Height / 2 + unitDistance * 2 + unitDistance * 2, paint5);
                        canvas.drawText("  공책: " + "700원 x" + thing4 + "개 =" + 700 * thing4 + "원", unitDistance / 2, Height / 2 + unitDistance * 2 + unitDistance * 3, paint5);
                        canvas.drawText("합계: " + 800 * thing1 + " + " + 1000 * thing2 + " + " + 500 * thing3 + " + " + 700 * thing4 + "= "
                                + sum + "원", unitDistance / 2, Height / 2 + unitDistance * 2 + unitDistance * 4 + unitDistance / 3, paint6);
                    } else {
                        canvas.drawText("물건 종류는 2개 이상 ", unitDistance / 2, Height / 2 + unitDistance * 2, paint6);
                        canvas.drawText("물건 개수는 4개 이상 구입해야 합니다!", unitDistance / 2, Height / 2 + unitDistance * 3, paint6);

                    }

                    canvas.drawBitmap(btnMyInfoClose.button_img, btnMyInfoClose.x, btnMyInfoClose.y, null);   //닫기 버튼

                }   //end of explain Window

                //설명 방법 안내창
                if (howToPlay == 1) {
                    canvas.drawBitmap(blueScreen, 0, 0, null);
                    canvas.drawText("사용설명서", unitDistance, unitDistance * 2, paint7);

                    canvas.drawText("1.구입할 물건을 선택합니다. 클릭할 때 마다 개수가 늘어남.", unitDistance, unitDistance * 3 + unitDistance / 4, paint2);

                    canvas.drawBitmap(thingGlue.button_img, thingGlue.x + unitDistance, thingGlue.y + unitDistance, null);
                    canvas.drawBitmap(thingScissors.button_img, thingScissors.x + unitDistance, thingScissors.y + unitDistance, null);
                    canvas.drawBitmap(thingEraser.button_img, thingEraser.x + unitDistance, thingEraser.y + unitDistance, null);
                    canvas.drawBitmap(thingBook.button_img, thingBook.x + unitDistance, thingBook.y + unitDistance, null);

                    canvas.drawText("2.물건 값에 맞도록 돈을 선택하여 지불할 돈을 정합니다. ", unitDistance, thingBook.y + thingBook.h * 2 + unitDistance + unitDistance + unitDistance / 3, paint2);
                    canvas.drawBitmap(manWon, unitDistance * 2, Height * 3 / 5 + unitDistance + unitDistance, null);
                    canvas.drawBitmap(cheonWon, Width / 3 + unitDistance, Height * 3 / 5 + unitDistance + unitDistance, null);
                    canvas.drawBitmap(baekWon, Width * 2 / 3 + unitDistance, Height * 3 / 5 + unitDistance + unitDistance, null);

                    canvas.drawText("3.물건을 4개 이상 구입해야 합니다!", unitDistance, Height / 2 + unitDistance * 6 + unitDistance / 2 - unitDistance / 4, paint2);

                    canvas.drawBitmap(btnMyInfoClose.button_img, btnMyInfoClose.x, btnMyInfoClose.y, null);   //닫기 버튼


                }  //end of howToPlay
            }

        //주인용
        if (myInfo == 0)
            if (selectionMenu == 2) {

                if(explainOk == 0 && howToPlay2 ==0) {
                    canvas.drawBitmap(backGround2, 0, 0, null);

                    canvas.drawBitmap(btnCheckAns.button_img, btnCheckAns.x, btnCheckAns.y, null);
                    canvas.drawBitmap(btnNextQue.button_img, btnNextQue.x, btnNextQue.y, null);

                    if (makingQestion == 1) {
                        double rand = Math.random();
                        thing1 = (int) (rand * (3));
                        double rand1 = Math.random();
                        thing2 = (int) (rand1 * (3));
                        double rand2 = Math.random();
                        thing3 = (int) (rand2 * (4));
                        double rand3 = Math.random();
                        thing4 = (int) (rand3 * (3));

                        //최소한 물건 두개는 구입한 것으로 하기 위해서
                        if ((thing1 + thing2 + thing3 + thing4) < 2) {
                            thing1 += 1;
                            thing3 += 1;
                        }
                        makingQestion = 0;
                    }

                    payMoney = 10000 - (thing1 * 800 + thing2 * 1000 + thing3 * 500 + thing4 * 700);
                    userMoney = userManwon * 10000 + userCheonwon * 1000 + userBaekwon * 100;

                    canvas.drawText(userMoney + "원", Width / 4 - unitDistance / 3, Height / 2 + unitDistance * 2 + unitDistance / 2, paint);
                    canvas.drawText("손님이 물건을 아래와 같이 고르고 만원을 냈습니다.", unitDistance, thingBook.y - thingBook.h / 2, paint5);
                    canvas.drawText("거스름돈을 주세요!", unitDistance, thingBook.y - thingBook.h / 7, paint5);
                    canvas.drawBitmap(thingGlue.button_img, thingGlue.x, thingGlue.y, null);
                    canvas.drawBitmap(thingScissors.button_img, thingScissors.x, thingScissors.y, null);
                    canvas.drawBitmap(thingEraser.button_img, thingEraser.x, thingEraser.y, null);
                    canvas.drawBitmap(thingBook.button_img, thingBook.x, thingBook.y, null);

                    canvas.drawText(thing1 + " 개", thingGlue.x + thingGlue.w / 2, thingGlue.y + thingGlue.h * 2 - thingGlue.h / 8, paint4);
                    canvas.drawText(thing2 + " 개", thingScissors.x + thingGlue.w / 2, thingScissors.y + thingGlue.h * 2 - thingGlue.h / 8, paint4);
                    canvas.drawText(thing3 + " 개", thingEraser.x + thingGlue.w / 2, thingEraser.y + thingGlue.h * 2 - thingGlue.h / 8, paint4);
                    canvas.drawText(thing4 + " 개", thingBook.x + thingGlue.w / 2, thingBook.y + thingGlue.h * 2 - thingGlue.h / 8, paint4);
                    if (userBaekwon == 10) {
                        userBaekwon = 0;
                        userCheonwon += 1;
                    }
                    if (userCheonwon == 10) {
                        userCheonwon = 0;
                        userManwon += 1;
                    }

                    if (userManwon >= 9) userManwon = 9;
                    if (userManwon <= 0) userManwon = 0;

                    if (userBaekwon <= 0 && userCheonwon == 0 && userManwon == 0) userBaekwon = 0;

                    canvas.drawBitmap(manWon, unitDistance * 2, Height * 3 / 5 + unitDistance + unitDistance / 3, null);

                    canvas.drawText(userManwon + "장", unitDistance + manWon_Width + unitDistance + unitDistance / 3, Height * 3 / 5 + unitDistance * 2 + unitDistance / 3, paint2);

                    canvas.drawBitmap(cheonWon, Width / 3 + unitDistance, Height * 3 / 5 + unitDistance + unitDistance / 3, null);
                    canvas.drawText(userCheonwon + "장", Width / 3 + manWon.getWidth() + unitDistance + unitDistance / 3, Height * 3 / 5 + unitDistance * 2 + unitDistance / 3, paint2);


                    canvas.drawBitmap(baekWon, Width * 2 / 3 + unitDistance, Height * 3 / 5 + unitDistance + unitDistance / 2, null);
                    canvas.drawText(userBaekwon + "개", Width * 2 / 3 + unitDistance * 3, Height * 3 / 5 + unitDistance * 2 + unitDistance / 3, paint2);

                    canvas.drawBitmap(btnAdd10000.button_img, btnAdd10000.x, btnAdd10000.y, null);
                    canvas.drawBitmap(btnMinus10000.button_img, btnMinus10000.x, btnMinus10000.y, null);
                    canvas.drawBitmap(btnAdd1000.button_img, btnAdd1000.x, btnAdd1000.y, null);
                    canvas.drawBitmap(btnMinus1000.button_img, btnMinus1000.x, btnMinus1000.y, null);
                    canvas.drawBitmap(btnAdd100.button_img, btnAdd100.x, btnAdd100.y, null);
                    canvas.drawBitmap(btnMinus100.button_img, btnMinus100.x, btnMinus100.y, null);
                } // 상점놀이 화면 끝

                if (explainOk == 1) {
                    canvas.drawBitmap(greenBoard, 0, Height / 2, null);
                    int sum = 800 * thing1 + 1000 * thing2 + 500 * thing3 + 700 * thing4;
                    canvas.drawBitmap(btnMyInfoClose.button_img, btnMyInfoClose.x, btnMyInfoClose.y, null);   //닫기 버튼
                    canvas.drawText("  연필: " + "800원 x" + thing1 + "개 =" + 800 * thing1 + "원", unitDistance / 2, Height / 2 + unitDistance + unitDistance / 5, paint5);
                    canvas.drawText("  가위: " + "1000원 x" + thing2 + "개 =" + 1000 * thing2 + "원", unitDistance / 2, Height / 2 + unitDistance + unitDistance / 5 + unitDistance, paint5);
                    canvas.drawText("  지우개: " + "500원 x" + thing3 + "개 =" + 500 * thing3 + "원", unitDistance / 2, Height / 2 + unitDistance + unitDistance / 5 + unitDistance * 2, paint5);
                    canvas.drawText("  공책: " + "700원 x" + thing4 + "개 =" + 700 * thing4 + "원", unitDistance / 2, Height / 2 + unitDistance + unitDistance / 5 + unitDistance * 3, paint5);
                    canvas.drawText("  손님이 지불한 돈 : " + 800 * thing1 + " + " + 1000 * thing2 + " + " + 500 * thing3 + " + " + 700 * thing4 + "=" + sum + "원",
                            unitDistance / 2, Height / 2 + unitDistance * 2 + unitDistance * 3 + unitDistance / 5, paint5);
                    canvas.drawText("  거스름돈   : 10000원 - " + sum + "= "
                            + (10000 - sum) + "원", unitDistance / 2, Height / 2 + unitDistance * 2 + unitDistance * 4 + unitDistance / 3 + unitDistance / 5, paint6);
                }

                //가게 주인용 설명 방법 안내창
                if (howToPlay2 == 1) {
                    canvas.drawBitmap(blueScreen, 0, 0, null);
                    canvas.drawText("사용설명서", unitDistance, unitDistance * 2, paint7);

                    canvas.drawText("1.손님이 고른 물건과 가격을 보고 거스름돈을 거슬러 줍니다.", unitDistance, unitDistance * 3 + unitDistance / 4, paint2);

                    canvas.drawBitmap(thingGlue.button_img, thingGlue.x + unitDistance, thingGlue.y + unitDistance, null);
                    canvas.drawBitmap(thingScissors.button_img, thingScissors.x + unitDistance, thingScissors.y + unitDistance, null);
                    canvas.drawBitmap(thingEraser.button_img, thingEraser.x + unitDistance, thingEraser.y + unitDistance, null);
                    canvas.drawBitmap(thingBook.button_img, thingBook.x + unitDistance, thingBook.y + unitDistance, null);

                    canvas.drawText(thing1 + " 개", thingGlue.x + thingGlue.w, thingGlue.y + thingGlue.h * 2 + unitDistance / 2, paint4);
                    canvas.drawText(thing2 + " 개", thingScissors.x + thingGlue.w, thingScissors.y + thingGlue.h * 2 + unitDistance / 2, paint4);
                    canvas.drawText(thing3 + " 개", thingEraser.x + thingGlue.w, thingEraser.y + thingGlue.h * 2 + unitDistance / 2, paint4);
                    canvas.drawText(thing4 + " 개", thingBook.x + thingGlue.w, thingBook.y + thingGlue.h * 2 + unitDistance / 2, paint4);


                    canvas.drawText("2.버튼을 클릭하여 거스름 돈을 정합니다. ", unitDistance, thingBook.y + thingBook.h * 2 + unitDistance + unitDistance + unitDistance / 3, paint2);
                    canvas.drawBitmap(manWon, unitDistance * 2, Height * 3 / 5 + unitDistance + unitDistance , null);
                    canvas.drawBitmap(cheonWon, Width / 3 + unitDistance, Height * 3 / 5 + unitDistance + unitDistance , null);
                    canvas.drawBitmap(baekWon, Width * 2 / 3 + unitDistance, Height * 3 / 5 + unitDistance + unitDistance , null);

                    canvas.drawBitmap(btnMyInfoClose.button_img, btnMyInfoClose.x, btnMyInfoClose.y, null);   //닫기 버튼
                }

            }


        if (submenuOk == 1 && myInfo == 0) {
            canvas.drawBitmap(blueScreen, 0, 0, null);

            canvas.drawText("학습목표", Width / 18, btnExit.h + btnExit.h / 2 - btnExit.w / 4 + unitDistance / 4, paint9);
            canvas.drawText("만, 십만, 백만, 천만, 억을 읽을 수 있다.", Width / 20, btnExit.h * 2 + btnExit.h / 2 - btnExit.w / 4 + unitDistance / 4, paint8);

            canvas.drawText("-가게놀이: 수와 가까와지는 활동을 합니다.", Width / 20, Height * 1 / 4 + unitDistance, paint8);
            canvas.drawText("-큰 수 학습하기: 만에서 억까지의 수를 학습합니다.", Width / 20, Height * 1 / 4 + unitDistance * 2 + unitDistance / 4, paint8);

            canvas.drawBitmap(btnExit.button_img, btnExit.x, btnExit.y, null);

            canvas.drawBitmap(btnMenu1.button_img, btnMenu1.x, btnMenu1.y, null);
            canvas.drawBitmap(btnMenu2.button_img, btnMenu2.x, btnMenu2.y, null);
            canvas.drawBitmap(btnMenu3.button_img, btnMenu3.x, btnMenu3.y, null);
        }

        if(explainOk == 1 && (selectionMenu==1 || selectionMenu == 2)) {
            if (answerValue == 1) {
                canvas.drawBitmap(answero, Width * 4 / 5, Height * 2 / 3 - unitDistance * 2, null);
                canvas.drawBitmap(greencoin, Width * 3 / 5, Height / 2 + unitDistance / 2, null);
                canvas.drawText(" 황금동전 1개 획득", Width * 3 / 5 + unitDistance, Height / 2 + unitDistance * 3, paint4);
            } else if (answerValue == 2)
                canvas.drawBitmap(answerx, Width * 4 / 5, Height * 2 / 3 - unitDistance * 2, null);
        }

        if (selectionMenu == 1 || selectionMenu == 2)
            if (myInfo == 1) {
                canvas.drawText("지금 맞은 개수 : " + oNumber, unitDistance, Height / 3, paint);
                canvas.drawText("지금 틀린 개수 : " + xNumber, unitDistance * 12, Height / 3, paint);
                canvas.drawText("총 맞은 개수 : " + toNumber, unitDistance, Height * 2 / 3, paint);
                canvas.drawText("총 틀린 개수 : " + txNumber, unitDistance * 12, Height * 2 / 3, paint);
                canvas.drawBitmap(btnMyInfoClose.button_img, btnMyInfoClose.x, btnMyInfoClose.y, null);   //닫기 버튼
                canvas.drawBitmap(sophia.button_img, sophia.x, sophia.y, null);
            }

        if (selectionMenu != 3)
            if (howToPlay == 0)
                canvas.drawBitmap(btnMyInfo.button_img, btnMyInfo.x, btnMyInfo.y, null);

        postInvalidate();
    } //end of onDraw

    private static Context getApplicationContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = 0, y = 0;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            x = (int) event.getX();
            y = (int) event.getY();

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

        }

        //새로 추가
        if (submenuOk == 1)
            if (x > btnExit.x && x < (btnExit.x + btnExit.w * 2) && y > btnExit.y && y < (btnExit.y + btnExit.h * 2)) {
                btnExit.btn_exit();

            }

        if (myInfo != 1)
            if (submenuOk == 0 && (selectionMenu == 1 || selectionMenu == 2 || selectionMenu == 3))
                if (x > btnExit.x && x < (btnExit.x + btnExit.w * 2) && y > btnExit.y && y < (btnExit.y + btnExit.h * 2)) {
                    myInfo = 0;
                    if (selectionMenu == 1 || selectionMenu == 4) {
                        submenuOk = 1;
                        explainOk = 0;
                        selectionMenu = 0;
                    } else btnExit.btn_exit(); //메인화면으로 나가기
                }


        if (submenuOk == 1 && myInfo == 0) {
            if (x > btnMenu1.x && x < (btnMenu1.x + btnMenu1.w * 2) && y > btnMenu1.y && y < (btnMenu1.y + btnMenu1.h * 2)) {
                selectionMenu = 1;
                submenuOk = 0;
            }

            if (x > btnMenu2.x && x < (btnMenu2.x + btnMenu2.w * 2) && y > btnMenu2.y && y < (btnMenu2.y + btnMenu2.h * 2)) {
                selectionMenu = 2;
                submenuOk = 0;
                makingQestion = 1;
            }
        }
        //덧셈에 대한 학습방법 selectionMenu=3;
//        if (submenuOk == 1 && myInfo == 0)
//            if (x > btnMenu2.x && x < (btnMenu2.x + btnMenu2.w * 2) && y > btnMenu2.y && y < (btnMenu2.y + btnMenu2.h * 2)) {
//                selectionMenu = 2;
//                submenuOk = 0;
//                makingQestion = 1;
//            }
//
//
//        if (submenuOk == 1 && myInfo == 0)
//            if (x > btnMenu3.x && x < (btnMenu3.x + btnMenu3.w * 2) && y > btnMenu3.y && y < (btnMenu3.y + btnMenu3.h * 2)) {
//                selectionMenu = 3;
//                submenuOk = 0;
//            }

        // 백, 십, 일  일씩 증가, 감소 시키기
        if (selectionMenu == 2)
            if (x > btnAdd10000.x && x < (btnAdd10000.x + btnAdd10000.w * 2) && y > btnAdd10000.y && y < (btnAdd10000.y + btnAdd10000.h * 2)) {
                userManwon += 1;
                if (userManwon >= 9) userManwon = 9;
                btn_manplus_press = 1;
                btnAdd10000.showPressedImage();
            }

        if (selectionMenu == 2)
            if (x > btnMinus10000.x && x < (btnMinus10000.x + btnMinus10000.w * 2) && y > btnMinus10000.y && y < (btnMinus10000.y + btnMinus10000.h * 2)) {
                userManwon -= 1;
                if (userManwon <= 0) userManwon = 0;
                btn_manminus_press = 1;
                btnMinus10000.showPressedImage();
            }

        //만원 돈
        if (selectionMenu == 1 || selectionMenu == 4 || selectionMenu == 3)

            if (x > btnAdd10000.x && x < (btnAdd10000.x + btnAdd10000.w * 2) && y > btnAdd10000.y && y < (btnAdd10000.y + btnAdd10000.h * 2)) {
                userManwon += 1;
                if (userManwon >= 9) userManwon = 9;
                btn_manplus_press = 1;
                btnAdd10000.showPressedImage();

            }

        if (selectionMenu == 1 || selectionMenu == 4 || selectionMenu == 3)
            if (x > btnMinus10000.x && x < (btnMinus10000.x + btnMinus10000.w * 2) && y > btnMinus10000.y && y < (btnMinus10000.y + btnMinus10000.h * 2)) {
                userManwon -= 1;
                if (userManwon <= 0) {
                    userManwon = 0;
                }
                btn_manminus_press = 1;
                btnMinus10000.showPressedImage();
            }

        if (selectionMenu == 2)
            if (x > btnAdd1000.x && x < (btnAdd1000.x + btnAdd1000.w * 2) && y > btnAdd1000.y && y < (btnAdd1000.y + btnAdd1000.h * 2)) {
                userCheonwon += 1;
                if (userCheonwon == 10) {
                    userCheonwon = 0;
                    userManwon += 1;
                }

                btnAdd1000.showPressedImage();
            }

        if (selectionMenu == 2)
            if (x > btnMinus1000.x && x < (btnMinus1000.x + btnMinus1000.w * 2) && y > btnMinus1000.y && y < (btnMinus1000.y + btnMinus1000.h * 2)) {
                userCheonwon -= 1;
                if (userCheonwon == 0) {
                    userCheonwon = 9;
                    userManwon -= 1;
                }

                btnMinus1000.showPressedImage();
            }

        if (selectionMenu == 1 || selectionMenu == 4 || selectionMenu == 3)
            if (x > btnAdd1000.x && x < (btnAdd1000.x + btnAdd1000.w * 2) && y > btnAdd1000.y && y < (btnAdd1000.y + btnAdd1000.h * 2)) {
                userCheonwon += 1;
                if (userCheonwon >= 9) userCheonwon = 9;

                btnAdd1000.showPressedImage();
            }

        if (selectionMenu == 1 || selectionMenu == 4 || selectionMenu == 3)
            if (x > btnMinus1000.x && x < (btnMinus1000.x + btnMinus1000.w * 2) && y > btnMinus1000.y && y < (btnMinus1000.y + btnMinus1000.h * 2)) {
                userCheonwon -= 1;
                if (userCheonwon <= 0) userCheonwon = 0;

                btnMinus1000.showPressedImage();
            }


        if (selectionMenu == 2)
            if (x > btnAdd100.x && x < (btnAdd100.x + btnAdd100.w * 2) && y > btnAdd100.y && y < (btnAdd100.y + btnAdd100.h * 2)) {
                userBaekwon += 1;
                if (userBaekwon == 0) {
                    if (userCheonwon == 9) {
                        userManwon += 1;
                        userCheonwon = 0;
                        userBaekwon = 9;
                    } else {
                        userBaekwon = 0;
                        userCheonwon += 1;
                    }
                }

                btnAdd100.showPressedImage();
            }

        if (selectionMenu == 2)     //일의 모형 1씩 증가하기
            if (x > btnMinus100.x && x < (btnMinus100.x + btnMinus100.w * 2) && y > btnMinus100.y && y < (btnMinus100.y + btnMinus100.h * 2)) {
                userBaekwon += 1;
                if (userManwon == 7 && userCheonwon == 9 && userBaekwon == 10) {
                    userBaekwon = 9;
                }
                if (userBaekwon == 10) {
                    if (userCheonwon == 9) {
                        userManwon += 1;
                        userBaekwon = 0;
                        userCheonwon = 0;
                    } else {
                        userBaekwon = 0;
                        userCheonwon += 1;
                    }
                }

                btnMinus100.showPressedImage();
            }

        if (selectionMenu == 1 || selectionMenu == 4 || selectionMenu == 3)
            if (x > btnAdd100.x && x < (btnAdd100.x + btnAdd100.w * 2) && y > btnAdd100.y && y < (btnAdd100.y + btnAdd100.h * 2)) {
                userBaekwon += 1;
                if (userBaekwon >= 9) userBaekwon = 9;

                btnAdd100.showPressedImage();
            }

        if (selectionMenu == 1 || selectionMenu == 4 || selectionMenu == 3)     //일의 모형 1씩 증가하기
            if (x > btnMinus100.x && x < (btnMinus100.x + btnMinus100.w * 2) && y > btnMinus100.y && y < (btnMinus100.y + btnMinus100.h * 2)) {
                userBaekwon -= 1;
                if (userBaekwon <= 0) userBaekwon = 0;

                btnMinus100.showPressedImage();
            }

// 내성적보기 버튼
        if (submenuOk == 0 && myInfo == 0 && selectionMenu != 3)
            if (x > btnMyInfo.x && x < (btnMyInfo.x + btnMyInfo.w * 2) && y > btnMyInfo.y && y < (btnMyInfo.y + btnMyInfo.h * 2)) {
                myInfo = 1;
                explainOk = 0;
            }

// 내성적보기 버튼 닫기 버튼

        if (submenuOk == 0)
            if (myInfo == 1)
                if (x > btnMyInfoClose.x && x < (btnMyInfoClose.x + btnMyInfoClose.w * 2) && y > btnMyInfoClose.y && y < (btnMyInfoClose.y + btnMyInfoClose.h * 2)) {
                    myInfo = 0;
                }


        // 내성적보기 버튼 닫기 버튼
        if (submenuOk == 0)
            if (howToPlay == 1)
                if (x > btnMyInfoClose.x && x < (btnMyInfoClose.x + btnMyInfoClose.w * 2) && y > btnMyInfoClose.y && y < (btnMyInfoClose.y + btnMyInfoClose.h * 2)) {
                    howToPlay = 0;
                }

        if (submenuOk == 0)
            if (howToPlay2 == 1)
                if (x > btnMyInfoClose.x && x < (btnMyInfoClose.x + btnMyInfoClose.w * 2) && y > btnMyInfoClose.y && y < (btnMyInfoClose.y + btnMyInfoClose.h * 2)) {
                    howToPlay2 = 0;
                }

        // 문제 풀이과정 및 정답 닫기 버튼
        if (selectionMenu == 1 || selectionMenu == 2)
            if (myInfo == 0)
                if (x > btnMyInfoClose.x && x < (btnMyInfoClose.x + btnMyInfoClose.w * 2) && y > btnMyInfoClose.y && y < (btnMyInfoClose.y + btnMyInfoClose.h * 2)) {
                    explainOk = 0;
                    userManwon = 0;
                    userCheonwon = 0;
                    userBaekwon = 0;
                    thing1 = 0;
                    thing2 = 0;
                    thing3 = 0;
                    thing4 = 0;
                    if(selectionMenu == 2) makingQestion = 1;
                    answerValue=0;
                }

        // 다시풀기  다음문제
        if (selectionMenu == 2)
            if (x > btnNextQue.x && x < (btnNextQue.x + btnNextQue.w * 2) && y > btnNextQue.y && y < (btnNextQue.y + btnNextQue.h * 2)) {
                makingQestion = 1; //주인용 게임에서 손님이 구입한 물건을 랜덤으로 만듬
                answerValue = 0;

                btnNextQue.showPressedImage();
                thing1 = 0;
                thing2 = 0;
                thing3 = 0;
                thing4 = 0;
                payMoney = 0;
                userManwon = 0;
                userCheonwon = 0;
                userBaekwon = 0;

            }

        if (explainOk == 0)
            if (submenuOk == 0 && (selectionMenu == 1 || selectionMenu == 2)) //정답확인
                if (x > btnCheckAns.x && x < (btnCheckAns.x + btnCheckAns.w * 2) && y > btnCheckAns.y && y < (btnCheckAns.y + btnCheckAns.h * 2)) {
                    explainOk = 1;

                    if (thing1 >= 1) numThing1 = 1;
                    else numThing1 = 0;
                    if (thing2 >= 1) numThing2 = 1;
                    else numThing2 = 0;
                    if (thing3 >= 1) numThing3 = 1;
                    else numThing3 = 0;
                    if (thing4 >= 1) numThing4 = 1;
                    else numThing4 = 0;

                    if (((thing1 + thing2 + thing3 + thing4) >= 4 && (numThing1 + numThing2 + numThing3 + numThing4) >= 2) || selectionMenu==2)
                        if (payMoney == userMoney) {
                            answerValue = 1;
                            oNumber += 1;
                            soundOk = 1;

                            sdPool.play(dingdongdaeng, 1.f, 1.f, 1, 0, 1.f);
                            try {
                                FileOutputStream outResult = mContext.openFileOutput("mFile.txt", Context.MODE_PRIVATE);
                                String str;

                                if (toNumber == "") toNumber = "0";
                                if (txNumber == "") txNumber = "0";

                                if ((thing1 + thing2 + thing3 + thing4) >= 2) {
                                    int num1 = Integer.parseInt(toNumber) + 1;
                                    toNumber = num1 + "";
                                }
                                str = toNumber + "|" + txNumber;
                                outResult.write(str.getBytes());
                                outResult.close();
                            } catch (IOException e) {
                            }


                        } else {
                            answerValue = 2;
                            xNumber += 1;
                            sdPool.play(taeng, 3, 3, 9, 0, 1);

                            try {
                                FileOutputStream outResult = mContext.openFileOutput("mFile.txt", Context.MODE_PRIVATE);
                                String str, s1, s2;
                                if (toNumber == "") toNumber = "0";
                                if (txNumber == "") txNumber = "0";
                                int num1 = Integer.parseInt(txNumber) + 1;
                                txNumber = num1 + "";

                                str = toNumber + "|" + txNumber;
                                outResult.write(str.getBytes());
                                outResult.close();
                            } catch (IOException e) {
                            }

                        }

                    btnCheckAns.showPressedImage();
                }

        if (explainOk == 0)
            if (selectionMenu == 1 ) {

                if (x > thingGlue.x && x < (thingGlue.x + thingGlue.w * 2) && y > thingGlue.y && y < (thingGlue.y + thingGlue.h * 2)) {
                    thing1 += 1;
                    btn_pressed1 = 1;
                    thingGlue.showPressedImage();
                }

                if (x > thingScissors.x && x < (thingScissors.x + thingScissors.w * 2) && y > thingScissors.y && y < (thingScissors.y + thingScissors.h * 2)) {
                    thing2 += 1;
                    btn_pressed2=1;
                    thingScissors.showPressedImage();
                }

                if (x > thingEraser.x && x < (thingEraser.x + thingEraser.w * 2) && y > thingEraser.y && y < (thingEraser.y + thingEraser.h * 2)) {
                    thing3 += 1;
                    btn_pressed3=1;
                    thingEraser.showPressedImage();
                }

                if (x > thingBook.x && x < (thingBook.x + thingBook.w * 2) && y > thingBook.y && y < (thingBook.y + thingBook.h * 2)) {
                    thing4 += 1;
                    btn_pressed4=1;
                    thingBook.showPressedImage();
                }

                //   if (selectionMenu == 1)
                if (x > thingNumInit.x && x < (thingNumInit.x + thingNumInit.w * 2) && y > thingNumInit.y && y < (thingNumInit.y + thingNumInit.h * 2)) {
                    thing1 = 0;
                    thing2 = 0;
                    thing3 = 0;
                    thing4 = 0;
                    userManwon = 0;
                    userCheonwon = 0;
                    userBaekwon = 0;

                    thingNumInit.showPressedImage();
                    btn_pressed5 = 1;
                    explainOk = 0;
                }
            }
        postInvalidate();
        // return gestureScanner.onTouchEvent(event);
        return false;
    }  //end of onTouchEvent

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;

        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                break;
            default:
        }
        return false;
    }
}
