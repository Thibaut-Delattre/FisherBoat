float FilTimer = 180;       // Timer pour pouvoir repêcher
float xFil;                 // Coordonnée x du fil
float yFil;                 // Coordonnée y du fil
float xHmc;                 // Coordonnée x de l'hammecon
float yHmc;                 // Coordonnée y de l'hammecon
float xBoat;                // Coordonnée x du bateau
float yBoat;                // Coordonnée y du bateau
float EcartHmc;             // Différence de coordonnée y du début du fil et de l'hammecon
float xFond;                // Coordonnée x du fond

float xBoatPourcent;        // Pourcentage de la distance parcouru du niveau
int scoreSave;              // Pour indiquer le score obtenue à la fin du niveau 
Boolean ResetBool = true;   // Pour reset une seule fois quand on le demande
int m=0;                    // Index pour les animations (change de dizaine)
int scene;                  // Quelle scene ?

PImage fond;                // Image du background
PImage fondecran;           // Image du background au menu
PImage fondecranflou;       // Image flou du background au menu 
PImage maplevel1;            // Image du niveau facile
PImage maplevel2;           // Image du niveau normal
PImage maplevel3;           // Image du niveau difficile
PImage ComboImg;            // Image des combos
PImage ScoreImg;            // Image des points marqués

PImage[] GreenFish = new PImage[60];     // Images pour l'animation des poissons verts
PImage[] YellowFish = new PImage[60];    // Images pour l'animation des poissons jaunes
PImage[] RedFish = new PImage[60];       // Images pour l'animation des poissons rouges
PImage[] GoldFish = new PImage[60];      // Images pour l'animation des poissons dorés
PImage[] boat = new PImage[60];          // Images pour l'animation du bateau
 
//----------SON----------// 
import ddf.minim.*;
import ddf.minim.spi.*;
import ddf.minim.signals.*;
import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.ugens.*;
import ddf.minim.effects.*;
Minim minim;
AudioPlayer ping;
AudioSample fishingroad;
AudioSample succes;
AudioSample coombooo;
AudioSample fail;
//----------------------//

void setup(){   // Fonction pour l'initialisation
  
  size(1500,800);      // taille en pixels du jeu
  
//--------------------------SON-------------------------//
  minim = new Minim(this);
  ping = minim.loadFile("Sea-noises.wav");
  fishingroad = minim.loadSample("fishingroad.wav");
  succes = minim.loadSample("succes.wav");
  coombooo = minim.loadSample("coombooo.wav");
  fail = minim.loadSample("fail.wav");
//------------------------------------------------------//  

  fondecran = loadImage("fondecran.jpg");          // Télécharge le jpg du background du menu
  fondecranflou = loadImage("fondecranflou.jpg");  // Télécharge le jpg flou du background du menu
  maplevel1 = loadImage("maplevel1.png");          // Télécharge le jpg de l'image du niveau facile
  maplevel2 = loadImage("maplevel2.png");          // Télécharge le jpg de l'image du niveau normal
  maplevel3 = loadImage("maplevel3.png");          // Télécharge le jpg de l'image du niveau difficile
  
//----------------------Animations----------------------//
  for(int k=0;k<=5;k++){
    for(int l=m;l<=m+9;l++){
    GreenFish[l] = loadImage("greenfish" + k + ".png");
    YellowFish[l] = loadImage("yellowfish" + k + ".png");
    RedFish[l] = loadImage("redfish" + k + ".png");
    GoldFish[l] = loadImage("goldfish" + k + ".png");
    boat[l] = loadImage("boat" + k + ".png");
    }
    m=m+10;
  }
//------------------------------------------------------//
 
  scene = 0;   // Le jeu se lance sur le menu principal
  
}

void draw(){  
   
  if(GreenFishCollider == false && scene == 1){      
    GreenMouseReleased = false;
    MouseReleased = false;
  }
  if(YellowFishCollider == false && scene == 1){
    YellowMouseReleased = false;
    MouseReleased = false;
  }
  if(RedFishCollider == false && scene == 1){
    RedMouseReleased = false;
    MouseReleased = false;
  }
  if(GoldFishCollider == false && scene == 1){
    GoldMouseReleased = false;
    MouseReleased = false;
  }
  
  
  xBoatPourcent = ((-6800 - xFond)/7500)*100;     // Calcul de la distance parcouru en pourcentage
  scoreSave = score;                              // Permet de sauvegarder le score pour l'indiquer à la fin du niveau
  
//----------------Changements de scenes----------------//
if(scene == 0){
  reset();
  Scene0();
}  
if(scene == 1){
  reset();
  textsAndBackground();
  CreateAllFishes();
  FishEverySteps(); 
  InvokeFishes();      
  ComboScore();
  BoatMoveOnlyInTheRiver();   
  Hmc_Boat_Fil();
}
if(scene == 2){    
  Scene2();
}
if(scene == 10){
    Scene10();
}
if(scene == 666){
    reset();
    Scenedeath();
} 
//-----------------------------------------------------//
}
