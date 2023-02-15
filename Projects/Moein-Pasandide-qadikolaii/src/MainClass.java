import processing.core.PApplet;
//import processing.sound.*;
import java.util.ArrayList;

public class MainClass extends PApplet {
    public static PApplet processing;
    static int rateOfFrame = 70;
    static int imageShowCounter = 0;
    static int letMenoGo = 0;
    static float menoTextsColorStart = 240;
    static float menoTextsColorLoading = 240;
    static float menoTextsColorExit = 240;
    static int countScore = 0;
    static int backgroundR = 125;
    static int backgroundG = 125;
    static int backgroundB = 125;
    static int gameLives = 5;
    static int justTestForGit = 3;

    static Ball ball = new Ball(5,5,252,223,0,200,180 , 20);
    Paddle paddle = new Paddle(200);
    static ArrayList<Block> blocks3 = new ArrayList<>();
    static ArrayList<Block> blocks2 = new ArrayList<>();
    static ArrayList<Block> blocks1 = new ArrayList<>();

    public static void main(String[] args) {
        PApplet.main("MainClass",args);
//        Scanner input = new Scanner(System.in);
//        System.out.println("please choose the size of ball");
//        int k = input.nextInt();
//        ball.setBallExtent(k);

    }

    public void setup(){
        processing = this;
//        size(640, 360);
        makeBlocks();

    }

    public void settings(){
        size(400,700);
    }

    public void draw(){

        if(letMenoGo == 0){
            meno();
        }else if(letMenoGo == 1){
            if ((blocks3.size() != 0) || (blocks2.size() != 0) || (blocks1.size() != 0)) {
                frameRate(rateOfFrame);
                if (imageShowCounter % 2 == 0) {
                    background(backgroundR, backgroundG, backgroundB);
                } else {
                    background(0);
                    int gridSize = 40;
                    for (int x = gridSize; x <= width - gridSize; x += gridSize) {
                        for (int y = gridSize; y <= height - gridSize; y += gridSize) {
                            noStroke();
                            fill(255);
                            rect(x - 1, y - 1, 3, 3);
                            stroke(255, 100);
                            line(x, y, width / 2, height / 2);
                        }
                    }
                }

                showBlocks();
                fill(237, 9, 9);
                rect(0, 630, 400, 5);
                ball.showBall();
                ball.changeBall();
                paddle.showPaddle();
                backMenoCurrentGame();
                exitCurrentGame();
                showScores();
                showLives();
                checkBallCrushed1(ball.getBallX(), ball.getBallY());
                checkBallCrushed2(ball.getBallX(), ball.getBallY());
                checkBallCrushed3(ball.getBallX(), ball.getBallY());
                paddle.movePaddle();
                if (((ball.getBallX() >= paddle.getX()) && (ball.getBallX() <= (paddle.getX() + paddle.getPaddleWidth())))
                        && ((ball.getBallY() + ball.getBallExtent() / 2 >= paddle.getY())
                        && (ball.getBallY() <= paddle.getY() + 20))) {
                    ball.setSpeedY(-(ball.getSpeedY()));
                }
            } else {
                background(0, 0, 0);
                fill(38, 235, 28);
                textSize(50);
                text("you won !!", 60, 250);
                textSize(35);
                text("Final Score : " + countScore , 60 , 310);
                text("remaining Live : " + gameLives , 60 , 370);
                backMenoCurrentGame();
                exitCurrentGame();
            }
        }else {
            gameOver();
            backMenoCurrentGame();
            exitCurrentGame();
        }

    }


    public static void gameOver(){
        if( gameLives > 0 )
        MainClass.gameLives--;
        if( MainClass.gameLives <= 0){
            MainClass.processing.background(0 , 0 , 0);
            MainClass.processing.textSize(64);
            MainClass.processing.fill(240,5,5);
            MainClass.processing.text("Game Over!!" , 10, 350);
            processing.textSize(35);
            processing.text("Final Score : " + countScore , 60 , 430);
            processing.text("remaining lives : " + gameLives , 60 , 490 );
        }else {
            letMenoGo = 1;
            ball.setBallX(200);
            ball.setBallY(180);
        }



    }




    public void mousePressed(){
        if(mouseButton == LEFT) {
            rateOfFrame -= 5;
        }else if(mouseButton == RIGHT){
            rateOfFrame += 5;
        }
    }

//    public void background(){
//
//    }
    float keyPressedK = 0;
    public void keyPressed(){

        switch ((int) keyPressedK) {
            case 0 -> {
                imageShowCounter = 0;
                backgroundR = 0;
                backgroundG = 0;
                backgroundB = 0;
            }
            case 1 -> {
                imageShowCounter = 0;
                backgroundR = 237;
                backgroundG = 7;
                backgroundB = 157;
            }
            case 2 -> {
                imageShowCounter = 1;

            }
        }
        keyPressedK++;
        if( keyPressedK == 3)  keyPressedK = 0;


    }


    public static void meno(){
        MainClass.processing.background(0 , 0 , 0);
        MainClass.processing.textSize(25);
        MainClass.processing.fill(240 , 236 , 17);
        MainClass.processing.text("welcome to break out game !!" , 20 , 100);
        MainClass.processing.textSize(20);
//        MainClass.processing.fill(200 , 200 , 200);
//        MainClass.processing.rect(29 , 182 , 125 , 20);
        // 20
        if( processing.mouseX >= 30 && processing.mouseX <= 155 && processing.mouseY >= 183 && processing.mouseY <= 202){
            menoTextsColorStart = 5;
        }else {
            menoTextsColorStart = 240;
        }
        if( processing.mouseX >= 30 && processing.mouseX <= 187 && processing.mouseY >= 223 && processing.mouseY <= 242){
                menoTextsColorLoading = 5;
        }else {
                menoTextsColorLoading = 240;
        }
        if( processing.mouseX >= 30 && processing.mouseX <= 85 && processing.mouseY >= 263 && processing.mouseY <= 282){
                menoTextsColorExit = 5;
        }else {
                menoTextsColorExit = 240;
        }

        MainClass.processing.fill(menoTextsColorStart , 236 , 17);
        MainClass.processing.text("1.Start Game" , 30 , 200);
        MainClass.processing.fill(menoTextsColorLoading , 236 , 17);
        MainClass.processing.text("2.Loading Game" , 30 , 240);
        MainClass.processing.fill(menoTextsColorExit , 236 , 17);
        MainClass.processing.text("3.Exit" , 30 , 280);

        if(processing.mousePressed){
//            rateOfFrame += 5;
            if( processing.mouseX >= 30 && processing.mouseX <= 155 && processing.mouseY >= 183 && processing.mouseY <= 202){
                letMenoGo = 1;
            }else if( processing.mouseX >= 30 && processing.mouseX <= 187 && processing.mouseY >= 223 && processing.mouseY <= 242 ){
                letMenoGo = 1;
            }else if(processing.mouseX >= 30 && processing.mouseX <= 85 && processing.mouseY >= 263 && processing.mouseY <= 282){
                System.exit(0);
            }
        }
    }

    public static void exitCurrentGame(){

        processing.textSize(20);
        if( processing.mouseX >=20 && processing.mouseX <= 120 && processing.mouseY >= 640 && processing.mouseY <= 665)
        {
            processing.fill(5,236,17);
        }else
            processing.fill(16 , 216 , 235);
        processing.rect(20 , 640 , 100 , 25);
        processing.fill(0 , 0 ,0);
        processing.text("Exit Game" , 20 , 660);

        if(processing.mousePressed){
            if(processing.mouseX >=20 && processing.mouseX <= 120 && processing.mouseY >= 640 && processing.mouseY <= 665){
                System.exit(0);

            }
        }
    }

    public static void backMenoCurrentGame(){

        processing.textSize(20);
        if( processing.mouseX >=20 && processing.mouseX <= 140 && processing.mouseY >= 670 && processing.mouseY <= 695)
        {
            processing.fill(5,236,17);
        }else
            processing.fill(16 , 216 , 235);
        processing.rect(20 , 670 , 120 , 25);
        processing.fill(0 , 0 ,0);
        processing.text("Back Menu" , 20 , 690);

        if(processing.mousePressed){

            if( processing.mouseX >=20 && processing.mouseX <= 140 && processing.mouseY >= 670 && processing.mouseY <= 695){
                letMenoGo = 0;
                rateOfFrame +=5;
            }
        }

    }

    public static void showScores(){
        processing.textSize(25);
        processing.fill(250 , 130 , 2);
        processing.text("Score : " + countScore , 10 , 25);
    }

    public static void showLives(){
        processing.textSize(25);
        processing.fill(250 , 130 , 2);
        processing.text("Lives : " + gameLives , 290 , 25);
    }


    public static void checkBallCrushed1(float a, float b){


        for(Block c : blocks1){
            if( (a >= c.getBlockX() - ball.getBallExtent()/2) && (a  - ball.getBallExtent()/2 <= c.getBlockX() + Block.blockWidth)
                    && (b - ball.getBallExtent()/2 <= c.getBlockY() + Block.blockHeight) && (b + ball.getBallExtent()/2 >= c.getBlockY()) ){

                if( (a + ball.getBallExtent()/2 >= c.getBlockX()) && (a - ball.getBallExtent()/2 <= c.getBlockX() + Block.blockWidth )
                && (b <= c.getBlockY() + Block.blockHeight + 1) && (b >= c.getBlockY() - 2) ){
                    ball.setSpeedX( -ball.getSpeedX() );
                    countScore +=10;
                    blocks1.remove(c);
                    break;
                }

                ball.setSpeedY( -ball.getSpeedY() );
                countScore +=10;
                blocks1.remove(c);
                break;
            }
        }

//        for(Block c : blocks1){
//            if( (b - ball.getBallExtent()/2 <= 120 + Block.blockHeight)
//                    && ( (a  >= c.getBlockX()) && (a <= c.getBlockX() + Block.blockWidth) ) ){
//                    ball.setSpeedY(  -ball.getSpeedY() );
//            }
//        }
//
//        blocks1.removeIf(c -> (b - ball.getBallExtent()/2 <= 120 + Block.blockHeight)
//                && ((a >= c.getBlockX()) && (a <= c.getBlockX() + Block.blockWidth)));
//
//
//        //////A MOO DI
//        for(Block c : blocks1) {
//            if ((b <= 120 + Block.blockHeight) && (b >= 120) && (a - ball.getBallExtent()/2 == c.getBlockX() + Block.blockWidth)
//            &&  (a + ball.getBallExtent()/2 == c.getBlockX()) ){
//                ball.setSpeedX( -ball.getSpeedX() );
//            }
//        }
//
//        blocks1.removeIf(c -> (b <= 120 + Block.blockHeight) && (b >= 120)
//                && (a - ball.getBallExtent()/2 == c.getBlockX() + Block.blockWidth) && (a + ball.getBallExtent()/2 == c.getBlockX()));


//        for(Block c : blocks1){
//            if( (b - ball.getBallExtent() <= c.getBlockY() + Block.blockHeight)
//                    && ( (a  >= c.getBlockX()) && (a <= c.getBlockX() + Block.blockWidth) ) ){
//                blocks1.remove(c);
//            }
////            MainClass.processing.background(0 , 0 , 0);
//        }

    }

    public static void checkBallCrushed2(float a,float b){


        for(Block c : blocks2){
            if( (a >= c.getBlockX() - ball.getBallExtent()/2) && (a  - ball.getBallExtent()/2 <= c.getBlockX() + Block.blockWidth)
                    && (b - ball.getBallExtent()/2 <= c.getBlockY() + Block.blockHeight) && (b + ball.getBallExtent()/2 >= c.getBlockY()) ){

                if( (a + ball.getBallExtent()/2 >= c.getBlockX()) && (a - ball.getBallExtent()/2 <= c.getBlockX() + Block.blockWidth )
                        && (b <= c.getBlockY() + Block.blockHeight + 1) && (b >= c.getBlockY() - 2) ){

                    if(c.getPotential() == 2){
                        c.setPotential( c.getPotential() -1);
                        c.setBlockColorR(137);
                        c.setBlockColorG(245);
                        c.setBlockColorB(73);
                        countScore +=10;
                        ball.setSpeedX( -ball.getSpeedX() );
                        break;
                    }else if(c.getPotential() == 1){
                        countScore +=20;
                        blocks2.remove(c);
                        ball.setSpeedX( -ball.getSpeedX() );
                        break;
                    }

                }


                if (c.getPotential() == 2) {
                    c.setPotential( c.getPotential() -1);
                    c.setBlockColorR(137);
                    c.setBlockColorG(245);
                    c.setBlockColorB(83);
                    countScore +=10;
                    ball.setSpeedY( -ball.getSpeedY() );
                }else if(c.getPotential() == 1){
                    ball.setSpeedY( -ball.getSpeedY() );
                    countScore +=20;
                    blocks2.remove(c);
                    break;
                }

            }
        }
    }


    public static void  checkBallCrushed3(float a,float b){


        for(Block c : blocks3){
            if( (a >= c.getBlockX() - ball.getBallExtent()/2) && (a  - ball.getBallExtent()/2 <= c.getBlockX() + Block.blockWidth)
                    && (b - ball.getBallExtent()/2 <= c.getBlockY() + Block.blockHeight) && (b + ball.getBallExtent()/2 >= c.getBlockY()) ){

                if( (a + ball.getBallExtent()/2 >= c.getBlockX()) && (a - ball.getBallExtent()/2 <= c.getBlockX() + Block.blockWidth )
                        && (b <= c.getBlockY() + Block.blockHeight + 1) && (b >= c.getBlockY() - 2) ){

                    if(c.getPotential() == 3){
                        c.setPotential( c.getPotential() -1);
                        c.setBlockColorR(237);
                        c.setBlockColorG(7);
                        c.setBlockColorB(207);
                        countScore +=10;
                        ball.setSpeedX( -ball.getSpeedX() );
                        break;
                        //237, 7, 207
                    }else if(c.getPotential() == 2){
                        c.setPotential( c.getPotential() -1);
                        c.setBlockColorR(252);
                        c.setBlockColorG(104);
                        c.setBlockColorB(233);
                        countScore +=20;
                        ball.setSpeedX( -ball.getSpeedX() );
                        break;

                    }else if(c.getPotential() == 1){
                        countScore +=30;
                        blocks3.remove(c);
                        ball.setSpeedX( -ball.getSpeedX() );
                        break;
                    }

                }

                if(c.getPotential() == 3){
                    c.setPotential( c.getPotential() -1);
                    c.setBlockColorR(237);
                    c.setBlockColorG(7);
                    c.setBlockColorB(207);
                    countScore +=10;
                    ball.setSpeedY( -ball.getSpeedY() );
                    break;
                }else if(c.getPotential() == 2){
                    c.setPotential( c.getPotential() -1);
                    c.setBlockColorR(252);
                    c.setBlockColorG(104);
                    c.setBlockColorB(233);
                    countScore +=20;
                    ball.setSpeedY( -ball.getSpeedY() );
                    break;

                }else if(c.getPotential() == 1){
                    countScore +=30;
                    blocks3.remove(c);
                    ball.setSpeedY( -ball.getSpeedY() );
                    break;
                }


//                if (c.getPotential() == 2) {
//                    c.setPotential( c.getPotential() -1);
//                    c.setBlockColorR(137);
//                    c.setBlockColorG(245);
//                    c.setBlockColorB(83);
//                    ball.setSpeedY( -ball.getSpeedY() );
//                }else if(c.getPotential() == 1){
//                    ball.setSpeedY( -ball.getSpeedY() );
//                    blocks2.remove(c);
//                    break;
//                }

            }
        }

    }

    public static void makeBlocks(){
        for (int i = 0; i < 6; i++) {
            blocks3.add(new Block(3,191, 6, 167, i*(Block.blockWidth + 15) + 25,40));
            MainClass.processing.noStroke();
            MainClass.processing.fill(blocks3.get(i).getBlockColorR(),blocks3.get(i).getBlockColorG(),blocks3.get(i).getBlockColorB());
            MainClass.processing.rect(blocks3.get(i).getBlockX(),40 ,Block.blockWidth,Block.blockHeight);
        }

        for (int i = 0; i < 6; i++) {
            blocks2.add(new Block(2,67,196,2, i*(Block.blockWidth + 15) + 25,80));
            MainClass.processing.noStroke();
            MainClass.processing.fill(blocks2.get(i).getBlockColorR(),blocks2.get(i).getBlockColorG(),blocks2.get(i).getBlockColorB());
            MainClass.processing.rect(blocks2.get(i).getBlockX(),80 ,Block.blockWidth,Block.blockHeight);
        }

        for (int i = 0; i < 6; i++) {
            blocks1.add(new Block(2,227,235,19, i*(Block.blockWidth + 15) + 25,120));
            MainClass.processing.noStroke();
            MainClass.processing.fill(blocks1.get(i).getBlockColorR(),blocks1.get(i).getBlockColorG(),blocks1.get(i).getBlockColorB());
            MainClass.processing.rect(blocks1.get(i).getBlockX(),120 ,Block.blockWidth,Block.blockHeight);
        }
    }

    public static void showBlocks(){
        for (int i = 0; i < blocks3.size(); i++) {
//            blocks3.add(new Block(3,205,9,227, i*(Block.blockWidth + 15) + 25,Block.blockHeight));
            MainClass.processing.noStroke();
            MainClass.processing.fill(blocks3.get(i).getBlockColorR(),blocks3.get(i).getBlockColorG(),blocks3.get(i).getBlockColorB());
            MainClass.processing.rect(blocks3.get(i).getBlockX(),40 ,Block.blockWidth,Block.blockHeight);
        }

        for (int i = 0; i < blocks2.size(); i++) {
//            blocks2.add(new Block(2,91,232,21, i*(Block.blockWidth + 15) + 25,Block.blockHeight));
            MainClass.processing.noStroke();
            MainClass.processing.fill(blocks2.get(i).getBlockColorR(),blocks2.get(i).getBlockColorG(),blocks2.get(i).getBlockColorB());
            MainClass.processing.rect(blocks2.get(i).getBlockX(),80 ,Block.blockWidth,Block.blockHeight);
        }

        for (int i = 0; i < blocks1.size(); i++) {
//            blocks1.add(new Block(2,227,235,19, i*(Block.blockWidth + 15) + 25,Block.blockHeight));
            MainClass.processing.noStroke();
            MainClass.processing.fill(blocks1.get(i).getBlockColorR(),blocks1.get(i).getBlockColorG(),blocks1.get(i).getBlockColorB());
            MainClass.processing.rect(blocks1.get(i).getBlockX(),120 ,Block.blockWidth,Block.blockHeight);
        }

    }


}






///