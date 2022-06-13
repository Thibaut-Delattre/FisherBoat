
//-----------------------------------------Fonction pour afficher que vous avez gagné-----------------------------------------//
void Scene2() {
  
   image(fondecranflou,0,0);
 
  
  fill(0,200,23);
  textSize(100);
  text("VOUS AVEZ GAGNE !",250,200);
  textSize(40);
  text("Vous avez parcouru " + round(xBoatPourcent) + " % de ce niveau ! Avec un score de " + round(scoreSave) + " points !" , 70,300);
  
  
  strokeJoin(ROUND);     // Contour arrondi
  stroke(RestartMenu);     // Couleur des contours
  strokeWeight(25);
  fill(RestartMenu);
  rect(xRestart,yRestart,widthRestart,heightRestart);
  fill(255,255,255);
  textSize(textSizeRestart);
  text("RECOMMENCER",xtextRestart,ytextRestart);
  
  if(mouseX >= 522 && mouseX <= 963 && mouseY >= 367 && mouseY <= 473){
    RestartMenu = color(75,75,75);
    xRestart = 537;
    yRestart = 387;
    widthRestart = 411;
    heightRestart = 75;
    textSizeRestart = 53;
    xtextRestart = 548;
    ytextRestart = 444;
  }
  else {
    RestartMenu = color(50,50,50);
    xRestart = 545;
    yRestart = 392;
    widthRestart = 395;
    heightRestart = 65;
    textSizeRestart = 50;
    xtextRestart = 555;
    ytextRestart = 440;
  }
  
  strokeJoin(ROUND);     // Contour arrondi
  stroke(MainBackMenu);     // Couleur des contours
  strokeWeight(25);
  fill(MainBackMenu);
  rect(xMainBack,yMainBack,widthMainBack,heightMainBack);
  fill(255,255,255);
  textSize(textSizeMainBack);
  text("RETOURNER AU MENU PRINCIPAL",xtextMainBack,ytextMainBack);
  
  if(mouseX >= 307 && mouseX <= 1175 && mouseY >= 527 && mouseY <= 629){
    MainBackMenu = color(75,75,75);
    xMainBack = 310;
    yMainBack = 545;
    widthMainBack = 880;
    heightMainBack = 75;
    textSizeMainBack = 53;
    xtextMainBack = 330;
    ytextMainBack = 602;
  }
  else {
    MainBackMenu = color(50,50,50);
    xMainBack = 330;
    yMainBack = 550;
    widthMainBack = 830;
    heightMainBack = 65;
    textSizeMainBack = 50;
    xtextMainBack = 345;
    ytextMainBack = 600;
  }
  
  
  if(MouseReleased == true && RestartMenu == color(75,75,75)){
    ResetBool = true;
    reset();
    scene = 1;
    MouseReleased = false;
  }
  if(MouseReleased == true && MainBackMenu == color(75,75,75)){
    ResetBool = true;
    scene = 0;
    MouseReleased = false;
  }
}
//----------------------------------------------------------------------------------------------------------------//
