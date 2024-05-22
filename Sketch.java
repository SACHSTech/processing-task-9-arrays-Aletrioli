import processing.core.PApplet;

public class Sketch extends PApplet {
	
  // snow variables
  float[] circleX = new float[50];
  float[] circleY = new float[circleX.length];
  boolean[] ballHidden = new boolean[circleX.length];
  float fltChange = 0;
  boolean boolChange = true;

  // player variables
  float fltPlayerX = 480;
  float fltPlayerY = 270;
  int intPlayerLives = 3;
  boolean iFrames = true;
  float timeSince = 0;
  float fltScore = 0;
  
  // directional booleans
  boolean boolUpArrow = false;
  boolean boolDownArrow = false;
  boolean boolUp = false;
  boolean boolDown = false;
  boolean boolRight = false;
  boolean boolLeft = false;

  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
    size(960, 540);
  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  public void setup() {
    background(0, 0, 0);
    strokeWeight(2);

    // randomly generate y
    for (int i = 0; i < circleX.length; i++) {
      circleY[i] = random(height);
      ballHidden[i] = false;
    }

    // randomly generate x
    for (int i = 0; i < circleX.length; i++) {
      //circleX[i] = circleX[(int)random(20)];
      circleX[i] = random(width);
    }
    
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    
    // stop everything except health (to show score and white screen) once lives <= 0
    if(intPlayerLives > 0){
      background(17, 0, 20);

      snowFall();

      playerCircle();
      collision();

      score();
    }

    playerHealth();
    

  }
  
  /**
   * Draws a circleX.length amount of ellipses which fall to the bottom of the screen and reappear at the top.
   * While score <= 1000 the ellipses move left and right, when score > 1000 the ellipses only move right
   */
  public void snowFall(){
    stroke(255, 255, 255);
    fill(255, 255, 255);

    // for loop enables every iteration to be accessed
    for (int i = 0; i < circleX.length; i++) {
      if(ballHidden[i] == false){
        ellipse(circleX[i], circleY[i], 25, 25);
      }
  
      // always change y by a constant
      circleY[i]++;
  
      if (boolUpArrow == true){
        circleY[i] -= 0.5;
      }
      if (boolDownArrow == true){
        circleY[i] += 1;
      }

      // change pattern if fltScore > 1000
      if(fltScore <= 1000){
        circleX[i] += fltChange;
        if (boolChange == true){
          fltChange += 0.001;
          if (fltChange >= 2.5){
            boolChange = false;
          }
        } else if (boolChange == false) {
          fltChange -= 0.001;
          if (fltChange <= -2.5 + (fltScore / 900)){
            boolChange = true;
          }
        }
      }

      if (fltScore >= 1000){
        circleX[i] += 2.5;
      }

      // if off screen, go to other side of screen
      if (circleY[i] > height) {
        circleY[i] = 0;
        circleX[i] = random(width);
        ballHidden[i] = false;
      }

      if (circleX[i] > width){
        circleX[i] = 0;
      } else if (circleX[i] <= 0){
        circleX[i] = width;
      }
    }
  }

  public void mousePressed(){
    // clickity click click (press snowball to make it go away)
    for (int i = 0; i < circleX.length; i++){
      if (dist(mouseX, mouseY, circleX[i], circleY[i]) < 12.5){
        ballHidden[i] = true;
      }
    }
  }

  public void playerCircle(){
    // if invincible, flash colours
    if(iFrames == true && millis() % 2 == 0){
      stroke(44, 77, 110);
      fill(102, 179, 255);
    } else if (iFrames == true){
      stroke(110, 40, 20);
      fill(255, 102, 179);
    } else {
      stroke(44, 77, 110);
      fill(102, 179, 255);
    }

    // movement
    if (boolUp == true){
      fltPlayerY -= 5;
    }
    if (boolDown == true){
      fltPlayerY += 5;
    }
    if (boolLeft == true){
      fltPlayerX -= 5;
    }
    if (boolRight == true){
      fltPlayerX += 5;
    }

    ellipse(fltPlayerX, fltPlayerY, 25, 25);    
  }

  public void playerHealth(){
    stroke(82, 0, 0);
    fill(255, 51, 51);

    // draw rectangles for health
    if (intPlayerLives == 3){
      rect(width - 35, 10, 25, 25);
    }
    if (intPlayerLives >= 2){
      rect(width - 60, 10, 25, 25);
    }
    if (intPlayerLives >= 1){
      rect(width - 85, 10, 25, 25);
    }

    // end game
    if (intPlayerLives <= 0){
      stroke(255);
      fill(255);
      rect(0, 0, width, height);
      
      fill(0);
      textSize(200);
      if(fltScore / 1000 <= 1){
        text((int)fltScore, 280, 340);
      } else {
        text((int)fltScore, 220, 340);
      }
      
    }

  }

  public void collision(){

    // check if touching
    for(int i = 0; i < circleX.length; i++){
      
      if(dist(fltPlayerX, fltPlayerY, circleX[i], circleY[i]) < 25 && ballHidden[i] == false && iFrames == false){
        intPlayerLives -= 1;
        iFrames = true;
        timeSince = millis();
      }
      if(iFrames == true){
        if (millis() >= timeSince + 1000){
          iFrames = false;
        }
      }

      if(fltPlayerX + 12.5 >= width && boolRight == true){
        fltPlayerX -= 5;
      }
      if(fltPlayerX - 12.5 <= 0 && boolLeft == true){
        fltPlayerX += 5;
      }
      if(fltPlayerY + 12.5 >= height && boolDown == true){
        fltPlayerY -= 5;
      }
      if(fltPlayerY - 12.5 <= 0 && boolUp == true){
        fltPlayerY += 5;
      }

    }
  }

  public void score() {
    // score
    fill(255);
    text((int)fltScore, 25, 25);
    fltScore += 0.5;

    if(boolDownArrow == true){
      fltScore += 1;
    }
    if(boolUpArrow == true){
      fltScore -= 1;
    }

  }

  public void keyPressed() {
    // change bools for movement and snow speed
    if (keyCode == UP){
      boolUpArrow = true;
    }
    if (keyCode == DOWN){
      boolDownArrow = true;
    }

    if (key == 'w'){
      boolUp = true;
    }
    if (key == 'a'){
      boolLeft = true;
    }
    if (key == 's'){
      boolDown = true;
    }
    if (key == 'd'){
      boolRight = true;
    }
  }

  public void keyReleased() {
    // change bools for movement and snow speed
    if (keyCode == UP){
      boolUpArrow = false;
    }
    if (keyCode == DOWN){
      boolDownArrow = false;
    }

    if (key == 'w'){
      boolUp = false;
    }
    if (key == 'a'){
      boolLeft = false;
    }
    if (key == 's'){
      boolDown = false;
    }
    if (key == 'd'){
      boolRight = false;
    }
  }

}