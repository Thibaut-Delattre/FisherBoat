color JouerMenu;
float xJouer;
float yJouer;
float widthJouer;
float heightJouer;
float textSizeJouer = 100;
float xtextJouer;
float ytextJouer;
color QuitMenu;
float xQuit;
float yQuit;
float widthQuit;
float heightQuit;
float textSizeQuit;
float xtextQuit;
float ytextQuit;

color FacileMenu;
float xFacile;
float yFacile;
float widthFacile;
float heightFacile;
float textSizeFacile;
float xtextFacile;
float ytextFacile;
color NormalMenu;
float xNormal;
float yNormal;
float widthNormal;
float heightNormal;
float textSizeNormal;
float xtextNormal;
float ytextNormal;
color DifficileMenu;
float xDifficile;
float yDifficile;
float widthDifficile;
float heightDifficile;
float textSizeDifficile;
float xtextDifficile;
float ytextDifficile;

color BackMenu;
float xBack;
float yBack;
float widthBack;
float heightBack;
float textSizeBack;
float xtextBack;
float ytextBack;

//----------------------------------------Boutons "jouer" et "quitter"----------------------------------------//
void Scene0(){
  
  image(fondecran,0,0);
  
  strokeJoin(ROUND);     // Contour arrondi
  stroke(JouerMenu);     // Couleur des contours
  strokeWeight(25);
  fill(JouerMenu);
  rect(xJouer,yJouer,widthJouer,heightJouer);
  fill(255,255,255);
  textSize(textSizeJouer);
  text("JOUER",xtextJouer,ytextJouer);
  
  if(mouseX >= 547 && mouseX <= 943 && mouseY >= 127 && mouseY <= 298){
    JouerMenu = color(75,75,75);
    xJouer = 562;
    yJouer = 142;
    widthJouer = 366;
    heightJouer = 136;
    textSizeJouer = 110;
    xtextJouer = 588;
    ytextJouer = 244;
  }
  else {
    JouerMenu = color(50,50,50);
    xJouer = 570;
    yJouer = 150;
    widthJouer = 350;
    heightJouer = 125;
    textSizeJouer = 100;
    xtextJouer = 600;
    ytextJouer = 250;
  }
  
  strokeJoin(ROUND);     // Contour arrondi
  stroke(QuitMenu);     // Couleur des contours
  strokeWeight(25);
  fill(QuitMenu);
  rect(xQuit,yQuit,widthQuit,heightQuit);
  fill(255,255,255);
  textSize(textSizeQuit);
  text("QUITTER",xtextQuit,ytextQuit);
  
  if(mouseX >= 482 && mouseX <= 1008 && mouseY >= 527 && mouseY <= 698){
    QuitMenu = color(75,75,75);
    xQuit = 497;
    yQuit = 542;
    widthQuit = 496;
    heightQuit = 136;
    textSizeQuit = 110;
    xtextQuit = 520;
    ytextQuit = 650;
  }
  else {
    QuitMenu = color(50,50,50);
    xQuit = 505;
    yQuit = 550;
    widthQuit = 480;
    heightQuit = 125;
    textSizeQuit = 100;
    xtextQuit = 535;
    ytextQuit = 650;
  }
  
  
  if(MouseReleased == true && JouerMenu == color(75,75,75)){
    scene = 10;
    MouseReleased = false;
  }
  if(MouseReleased == true && QuitMenu == color(75,75,75)){
    exit();
  }
  
}
//------------------------------------------------------------------------------------------------------------// 
  
  
  
//----------------------------------------Boutons "facile", "normal" et "difficile"----------------------------------------//
  void Scene10(){
  
  image(fondecranflou,0,0);
  
  strokeJoin(ROUND);     // Contour arrondi
  stroke(FacileMenu);     // Couleur des contours
  strokeWeight(25);
  fill(FacileMenu);
  rect(xFacile,yFacile,widthFacile,heightFacile);
  fill(255,255,255);
  textSize(textSizeFacile);
  text("FACILE",xtextFacile,ytextFacile);
  
  if(mouseX >= 27 && mouseX <= 420 && mouseY >= 127 && mouseY <= 298){
    FacileMenu = color(75,75,75);
    xFacile = 50;
    yFacile = 142;
    widthFacile = 400;
    heightFacile = 136;
    textSizeFacile = 110;
    xtextFacile = 65;
    ytextFacile = 244;
    image(maplevel1,650,155);
  }
  else {
    FacileMenu = color(50,50,50);
    xFacile = 50;
    yFacile = 150;
    widthFacile = 350;
    heightFacile = 125;
    textSizeFacile = 100;
    xtextFacile = 65;
    ytextFacile = 250;
  }
  
  strokeJoin(ROUND);     // Contour arrondi
  stroke(NormalMenu);     // Couleur des contours
  strokeWeight(25);
  fill(NormalMenu);
  rect(xNormal,yNormal,widthNormal,heightNormal);
  fill(255,255,255);
  textSize(textSizeNormal);
  text("NORMAL",xtextNormal,ytextNormal);
  
  if(mouseX >= 27 && mouseX <= 530 && mouseY >= 327 && mouseY <= 498){
    NormalMenu = color(75,75,75);
    xNormal = 50;
    yNormal = 342;
    widthNormal = 500;
    heightNormal = 136;
    textSizeNormal = 110;
    xtextNormal = 65;
    ytextNormal = 447;
    image(maplevel2,650,355);
  }
  else {
    NormalMenu = color(50,50,50);
    xNormal = 50;
    yNormal = 350;
    widthNormal = 460;
    heightNormal = 125;
    textSizeNormal = 100;
    xtextNormal = 65;
    ytextNormal = 450;
  }
  
  strokeJoin(ROUND);     // Contour arrondi
  stroke(DifficileMenu);     // Couleur des contours
  strokeWeight(25);
  fill(DifficileMenu);
  rect(xDifficile,yDifficile,widthDifficile,heightDifficile);
  fill(255,255,255);
  textSize(textSizeDifficile);
  text("DIFFICILE",xtextDifficile,ytextDifficile);
  
  if(mouseX >= 27 && mouseX <= 550 && mouseY >= 527 && mouseY <= 698){
    DifficileMenu = color(75,75,75);
    xDifficile = 50;
    yDifficile = 542;
    widthDifficile = 525;
    heightDifficile = 136;
    textSizeDifficile = 110;
    xtextDifficile = 65;
    ytextDifficile = 650;
    image(maplevel3,650,555);
  }
  else {
    DifficileMenu = color(50,50,50);
    xDifficile = 50;
    yDifficile = 550;
    widthDifficile = 480;
    heightDifficile = 125;
    textSizeDifficile = 100;
    xtextDifficile = 65;
    ytextDifficile = 650;
  }
  
  
  strokeJoin(ROUND);     // Contour arrondi
  stroke(BackMenu);     // Couleur des contours
  strokeWeight(25);
  fill(BackMenu);
  rect(xBack,yBack,widthBack,heightBack);
  fill(255,255,255);
  textSize(textSizeBack);
  text("RETOUR",xtextBack,ytextBack);
  
  if(mouseX >= 622 && mouseX <= 868 && mouseY >= 690 && mouseY <= 790){
    BackMenu = color(75,75,75);
    xBack = 635;
    yBack = 715;
    widthBack = 230;
    heightBack = 60;
    textSizeBack = 50;
    xtextBack = 653;
    ytextBack = 763;
  }
  else {
    BackMenu = color(50,50,50);
    xBack = 645;
    yBack = 720;
    widthBack= 210;
    heightBack = 50;
    textSizeBack = 40;
    xtextBack = 670;
    ytextBack = 760;
  }
  
   if(MouseReleased == true && FacileMenu == color(75,75,75) ){
    ResetBool = true;
    fond = loadImage("background1.jpg");    // Télécharge l'image du background
    SpeedFishes = 1.1;
    scene = 1;
    MouseReleased = false;
  }
  if(MouseReleased == true && NormalMenu == color(75,75,75)){
    ResetBool = true;
    fond = loadImage("background2.jpg");    // Télécharge l'image du background
    SpeedFishes = 1.4;
    scene = 1;
    MouseReleased = false;
  }
  if(MouseReleased == true && DifficileMenu == color(75,75,75)){
    ResetBool = true;
    fond = loadImage("background3.jpg");    // Télécharge l'image du background
    SpeedFishes = 1.9;
    scene = 1;
    MouseReleased = false;
  }
  if(MouseReleased == true && BackMenu == color(75,75,75)){
    scene = 0;
    MouseReleased = false;
  }
 }
//-----------------------------------------------------------------------------------------------------// 
