import processing.core.PApplet;

public class Sketch extends PApplet {
	
	int[] circleX = {10, 35, 50, 70, 80, 90, 115, 125, 150, 175, 190, 215, 240, 265, 290, 305, 320, 340, 365, 385};
  int[] circleXSelect = new int[circleX.length];
  float[] circleY = new float[circleX.length];
  boolean[] ballHidden = new boolean[circleX.length];

  boolean boolTakeDamage = true;
  int intHurt = 0;

  float fltPlayerX = 175;
  float fltPlayerY = 175;
  int intPlayerLives = 3;
  
  boolean boolUpArrow = false;
  boolean boolDownArrow = false;

  float fltScore = 0;

  boolean boolUp = false;
  boolean boolDown = false;
  boolean boolRight = false;
  boolean boolLeft = false;

  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
    size(400, 400);
  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  public void setup() {
    background(0, 0, 0);
    strokeWeight(2);

    for (int i = 0; i < circleY.length; i++) {
      circleY[i] = random(height);
      ballHidden[i] = false;
    }

    for (int i = 0; i < circleX.length; i++) {
      circleXSelect[i] = circleX[(int)random(20)];
    }
    
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    
    background(17, 0, 20);

    snowFall();

    playerCircle();
    playerHealth();
    collision();

    score();

  }
  
  public void snowFall(){
    stroke(255, 255, 255);
    fill(255, 255, 255);
    for (int i = 0; i < circleY.length; i++) {
      // float circleX = 50 * (i + 1);
      if(ballHidden[i] == false){
        ellipse(circleXSelect[i], circleY[i], 25, 25);
      }
  
      circleY[i]++;
  
      if (boolUpArrow == true){
        circleY[i] -= 0.5;
      }
      if (boolDownArrow == true){
        circleY[i] += 1;
      }

      if (fltScore >= 1000){
        circleXSelect[i]++;
      }

      if (circleY[i] > height) {
        circleY[i] = 0;
        circleXSelect[i] = circleX[(int)random(20)];
        ballHidden[i] = false;
      }

      if (circleXSelect[i] > width){
        circleXSelect[i] = 0;
      }
    }
  }

  public void mouseClicked(){
    for (int i = 0; i < circleX.length; i++){
      if (dist(mouseX, mouseY, circleXSelect[i], circleY[i]) < 12.5){
        ballHidden[i] = true;
      }
    }
  }

  public void playerCircle(){
    stroke(44, 77, 110);
    fill(102, 179, 255);

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

    if (intPlayerLives == 3){
      rect(365, 10, 25, 25);
    }
    if (intPlayerLives >= 2){
      rect(340, 10, 25, 25);
    }
    if (intPlayerLives >= 1){
      rect(315, 10, 25, 25);
    }

    stroke(255);
    fill(255);
    if (intPlayerLives <= 0){
      rect(0, 0, width, height);
    }

  }

  public void collision(){
    for(int i = 0; i < circleX.length; i++){
      if(dist(fltPlayerX, fltPlayerY, circleXSelect[i], circleY[i]) < 25 && boolTakeDamage == true && ballHidden[i] == false){
        intPlayerLives -= 1;
        boolTakeDamage = false;
        intHurt = i;
      }
      if(dist(fltPlayerX, fltPlayerY, circleXSelect[intHurt], circleY[intHurt]) > 25 && boolTakeDamage == false){
        boolTakeDamage = true;
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

  public void score() {
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