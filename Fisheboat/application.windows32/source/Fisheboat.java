import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.spi.*; 
import ddf.minim.signals.*; 
import ddf.minim.*; 
import ddf.minim.analysis.*; 
import ddf.minim.ugens.*; 
import ddf.minim.effects.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Fisheboat extends PApplet {

float FilTimer = 180;       // Timer pour pouvoir rep\u00eacher
float xFil;                 // Coordonn\u00e9e x du fil
float yFil;                 // Coordonn\u00e9e y du fil
float xHmc;                 // Coordonn\u00e9e x de l'hammecon
float yHmc;                 // Coordonn\u00e9e y de l'hammecon
float xBoat;                // Coordonn\u00e9e x du bateau
float yBoat;                // Coordonn\u00e9e y du bateau
float EcartHmc;             // Diff\u00e9rence de coordonn\u00e9e y du d\u00e9but du fil et de l'hammecon
float xFond;                // Coordonn\u00e9e x du fond

float xBoatPourcent;        // Pourcentage de la distance parcouru du niveau
int scoreSave;              // Pour indiquer le score obtenue \u00e0 la fin du niveau 
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
PImage ScoreImg;            // Image des points marqu\u00e9s

PImage[] GreenFish = new PImage[60];     // Images pour l'animation des poissons verts
PImage[] YellowFish = new PImage[60];    // Images pour l'animation des poissons jaunes
PImage[] RedFish = new PImage[60];       // Images pour l'animation des poissons rouges
PImage[] GoldFish = new PImage[60];      // Images pour l'animation des poissons dor\u00e9s
PImage[] boat = new PImage[60];          // Images pour l'animation du bateau
 
//----------SON----------// 







Minim minim;
AudioPlayer ping;
AudioSample fishingroad;
AudioSample succes;
AudioSample coombooo;
AudioSample fail;
//----------------------//

public void setup(){   // Fonction pour l'initialisation
  
  size(1500,800);      // taille en pixels du jeu
  
//--------------------------SON-------------------------//
  minim = new Minim(this);
  ping = minim.loadFile("Sea-noises.wav");
  fishingroad = minim.loadSample("fishingroad.wav");
  succes = minim.loadSample("succes.wav");
  coombooo = minim.loadSample("coombooo.wav");
  fail = minim.loadSample("fail.wav");
//------------------------------------------------------//  

  fondecran = loadImage("fondecran.jpg");          // T\u00e9l\u00e9charge le jpg du background du menu
  fondecranflou = loadImage("fondecranflou.jpg");  // T\u00e9l\u00e9charge le jpg flou du background du menu
  maplevel1 = loadImage("maplevel1.png");          // T\u00e9l\u00e9charge le jpg de l'image du niveau facile
  maplevel2 = loadImage("maplevel2.png");          // T\u00e9l\u00e9charge le jpg de l'image du niveau normal
  maplevel3 = loadImage("maplevel3.png");          // T\u00e9l\u00e9charge le jpg de l'image du niveau difficile
  
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

public void draw(){  
   
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
  scoreSave = score;                              // Permet de sauvegarder le score pour l'indiquer \u00e0 la fin du niveau
  
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
int i;                                   // Index du tableau des poissons
int j;                                   // Index pour faire appara\u00eetre les poissons toutes les 100 frames
float SpeedFishes;                       // Vitesse sur l'axe x des poissons
int ColorReadByHmc;                    // Couleur qui prendra la valeur rgb du pixel \u00e0 droite de l'hammecon
float DisplayFishTimer;                  // Timer pour afficher un poisson toutes les 100 frames
int RandomColors;                        // Index pour la randomisation des couleurs des poissons

boolean GreenFishCollider = false;       // Le hammecon touche un poisson vert ?
boolean YellowFishCollider = false;      // Le hammecon touche un poisson jaune ?
boolean RedFishCollider = false;         // Le hammecon touche un poisson rouge ?
boolean GoldFishCollider = false;        // Le hammecon touche un poisson dor\u00e9 ?

boolean GreenMouseReleased;              // Le click est realch\u00e9 pour un poisson vert ?
boolean YellowMouseReleased;             // Le click est realch\u00e9 pour un poisson jaune ?
boolean RedMouseReleased;                // Le click est realch\u00e9 pour un poisson rouge ?
boolean GoldMouseReleased;               // Le click est realch\u00e9 pour un poisson dor\u00e9 ?
boolean MouseReleased = false;           // Le click est relach\u00e9 ?

float ComboGreenTimer;                   // Timer pour le combo des poissons verts
float ComboRedTimer;                     // Timer pour le combo des poissons verts
float ComboYellowTimer;                  // Timer pour le combo des poissons verts
float ComboGoldTimer;                    // Timer pour le combo des poissons verts
float ComboScoreTimer;                   // Timer pour le combo des poissons verts

boolean ComboScoreX2;                    // Pour faire durer l'apparition de l'image "X2"
boolean ComboScoreX3;                    // Pour faire durer l'apparition de l'image "X3"
boolean ComboScoreX4;                    // Pour faire durer l'apparition de l'image "X4"
boolean ComboScoreX5;                    // Pour faire durer l'apparition de l'image "X5"
boolean Score1;                          // Pour faire durer l'apparition de l'image "+1"
boolean Score2;                          // Pour faire durer l'apparition de l'image "+2"  
boolean Score4;                          // Pour faire durer l'apparition de l'image "+5"
boolean Score8;                          // Pour faire durer l'apparition de l'image "+15"
boolean Scoremoins1;                     // Pour faire durer l'apparition de l'image "-1"
boolean CreateAllFishesBool;             // Pour permettre de cr\u00e9er qu'une seule fois les 100 poissons

Fishes[] f = new Fishes[100];   // On cr\u00e9\u00e9 un tableau de 100 poissons

int ColorsR[] = {30,30,30,30,30,30,30,30,30,255,255,255,255,255,255,222,222,222,212};          // 50 % Vert ; 30 % Jaune ;
int ColorsG[] = {205,205,205,205,205,205,205,205,205,225,225,225,225,225,225,18,18,18,175};    // 15 % rouge ; 5 % Dor\u00e9
int ColorsB[] = {12,12,12,12,12,12,12,12,12,8,8,8,8,8,8,18,18,18,55};                          //


public void FishEverySteps(){            // Fonction pour faire appara\u00eetre toutes les 100 frames un poisson 
  
 if(xBoatPourcent <= 100){             // Tant que le bateau n'a pas fini le niveau
  if(DisplayFishTimer >= 1){           // Timer
     DisplayFishTimer--;               //
  }                                    //
  for(i=0;i<=99;i++){
    if(DisplayFishTimer <= 0){ 
      f[j].DisplayFishEverySteps();    // Tous les 100 frames on fait tourner cette fonction
      j++;
      DisplayFishTimer = 100;          // Reboot le Timer
    }
   }
  }
}

public void CreateAllFishes(){       // Fonction pour cr\u00e9er les 100 poissons
  
  if(CreateAllFishesBool == true){
     for(i=0;i<=99;i++){
        RandomColors = (int)random(0, 19);
        f[i] = new Fishes(xBoat + (-500), 500, 50, 50, random(-1,1), random(0,120), color(ColorsR[RandomColors], ColorsG[RandomColors],
        ColorsB[RandomColors]), false, false, false, false, color(235,72,47), color(235,72,47), color(235,72,47), color(235,72,47));     
        CreateAllFishesBool = false;        // Pour que les poissons soient cr\u00e9\u00e9s qu'une seule fois
     }
   }
}


//--------------------------------------POISSON CLASS--------------------------------------//

class Fishes {          // On cr\u00e9\u00e9 un nouvel object : les poissons
  
//------------------------------------DATA------------------------------------//
  float x;                             // Coordonn\u00e9 x des poissons       
  float y;                             // Coordonn\u00e9 y des poissons                                  
  float Width;                         // Longueur des poissons
  float Height;                        // Hauteur des poissons
  float yRandom;                       // Vitesse al\u00e9atoire sur l'axe y des poissons
  float RandomFish;                    // Intervalle des changememts de yRandom
  int Color;                         // Couleur des poissons
  boolean GreenFishGrabed = false;     // Poisson vert attrap\u00e9
  boolean YellowFishGrabed = false;    // Poisson jaune attrap\u00e9
  boolean RedFishGrabed = false;       // Poisson rouge attrap\u00e9
  boolean GoldFishGrabed = false;      // Poisson dor\u00e9 attrap\u00e9
  int ColorReadTopRight;             // Couleur du pixel en haut \u00e0 droite du poisson
  int ColorReadTopLeft;              // Couleur du pixel en haut \u00e0 gauche du poisson
  int ColorReadBottomRight;          // Couleur du pixel en bas \u00e0 droite du poisson
  int ColorReadBottomLeft;           // Couleur du pixel en bas \u00e0 gauche du poisson
//----------------------------------------------------------------------------//
  
  Fishes(){}        // --> Constructeur vide

//---------------------------------------------------------------------------------------Constructeur---------------------------------------------------------------------------------------//
  Fishes(float newxFish, float newyFish, float newWidth, float newHeight, float newyRandom, float newRandomFish, int newColor, boolean newGreenFishGrabed, boolean newYellowFishGrabed,    
  boolean newRedFishGrabed, boolean newGoldFishGrabed, int newColorReadTopRight, int newColorReadTopLeft, int newColorReadBottomRight, int newColorReadBottomLeft){
    
    x = newxFish;
    y = newyFish;
    Width = newWidth;
    Height = newHeight;
    yRandom = newyRandom;
    RandomFish = newRandomFish;
    Color = newColor;
    GreenFishGrabed = newGreenFishGrabed;
    YellowFishGrabed = newYellowFishGrabed;
    RedFishGrabed = newRedFishGrabed;
    GoldFishGrabed = newGoldFishGrabed;
    ColorReadTopRight = newColorReadTopRight;
    ColorReadTopLeft = newColorReadTopLeft;
    ColorReadBottomRight = newColorReadBottomRight;
    ColorReadBottomLeft = newColorReadBottomLeft;
  }
  //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
  
  public void DisplayFishEverySteps(){    // Fonction qui permet d'afficher les poissons toutes les 100 frames \u00e0 droite de l'\u00e9cran

  if(i<=99){                      // Pour les 100 poissons cr\u00e9\u00e9s, chacun sont d\u00e9plac\u00e9s toutes les 100 frames (voir "void FishEverySteps(){}")
    f[j].x = xBoat + 1340;        // \u00e0 droite de l'\u00e9cran 
  }  
}

  public void DisplayFish(){        // Fonction qui pemet d'afficher les poissons avec leurs principales caract\u00e9ristiques
    
    f[i].RandomFish ++;      // Timer du changement de vitesse sur l'axe y
    
    if(f[i].Color == color(30,205,12)) {                     // --> Si le poisson est vert
      Width = 107;                                           //     -> Sa largeur est de 107 pixels       
      Height = 74;                                           //     -> Sa hauteur est de 74 pixels
      if(f[i].RandomFish >= 120){                            //     -> Toutes les 2 secondes (120 frames), 
         f[i].yRandom = random(-1,1);                        //        - Ce poisson change de vitesse au hasard sur l'axe y entre -1 et 1
         f[i].RandomFish = 0;                                //        - Reset au bout de 2 secondes le timer du changement de vitesse sur l'axe y
      }                                                      //
      f[i].y =  f[i].y +  f[i].yRandom;                      //     -> Ce poisson bouge sur l'axe y avec la vitesse al\u00e9atoire sur l'axe y
      x = x - SpeedFishes;                                   //     -> Ce poisson avance sur l'axe x \u00e0 une certaine vitesse selon le niveau choisi   
      image(GreenFish[frameCount%60],x,y);                   //     -> Permet d'afficher le poisson avec son animation
    }                                                        //
    
    
     if(f[i].Color == color(255,225,8)) {                    // --> Si le poisson est jaune
      Width = 79;                                            //     -> Sa largeur est de 79 pixels
      Height = 63;                                           //     -> Sa hauteur est de 63 pixels
     if(f[i].RandomFish >= 90){                              //     -> Toutes les 1.5 secondes (90 frames),
         f[i].yRandom = random(-1.5f,1.5f);                    //        - Ce poisson change de vitesse au hasard sur l'axe y entre...
         if(f[i].yRandom >= 0 && f[i].yRandom < 0.5f){        //
           f[i].yRandom = 0.5f;                               //        ... 0.5 et 1.5...
         }                                                   //        ... et entre...
         if(f[i].yRandom < 0 && f[i].yRandom > -0.5f){        //
           f[i].yRandom = -0.5f;                              //        ... -1.5 et -0.5
         }                                                   //
         f[i].RandomFish = 0;                                //        - Reset au bout de 1.5 secondes le timer du changement de vitesse sur l'axe y    
      }                                                      //
      f[i].y =  f[i].y +  f[i].yRandom;                      //     -> Ce poisson bouge sur l'axe y avec la vitesse al\u00e9atoire sur l'axe y
      x = x - SpeedFishes;                                   //     -> Ce poisson avance sur l'axe x \u00e0 une certaine vitesse selon le niveau choisi
      image(YellowFish[frameCount%60],x,y);                  //     -> Permet d'afficher le poisson avec son animation
    }                                                        //
    
    
     if(f[i].Color == color(222,18,18)) {                    // --> Si le poisson est rouge
      Width = 81;                                            //     -> Sa largeur est de 81 pixels
      Height = 49;                                           //     -> Sa hauteur est de 49 pixels
     if(f[i].RandomFish >= 60){                              //     -> Toutes les 1 secondes (60 frames),
         f[i].yRandom = random(-2,2);                        //        - Ce poisson change de vitesse au hasard sur l'axe y entre...
         if(f[i].yRandom >= 0 && f[i].yRandom < 1){          //
           f[i].yRandom = 1;                                 //        ... 1 et 2...
         }                                                   //        ... et entre...
         if(f[i].yRandom < 0 && f[i].yRandom > -1){          //
           f[i].yRandom = -1;                                //        ... -2 et 1
         }                                                   //
         f[i].RandomFish = 0;                                //        - Reset au bout de 1 seconde le timer du changement de vitesse sur l'axe y    
      }                                                      //
      f[i].y =  f[i].y +  f[i].yRandom;                      //     -> Ce poisson bouge sur l'axe y avec la vitesse al\u00e9atoire sur l'axe y
      x = x - SpeedFishes;                                   //     -> Ce poisson avance sur l'axe x \u00e0 une certaine vitesse selon le niveau choisi
      image(RedFish[frameCount%60],x,y);                     //     -> Permet d'afficher le poisson avec son animation
    }                                                        //
    
    
     if(f[i].Color == color(212,175,55)) {                   // --> Si le poisson est dor\u00e9
      Width = 52;                                            //     -> Sa largeur est de 52 pixels
      Height = 35;                                           //     -> Sa hauteur est de 35 pixels
     if(f[i].RandomFish >= 60){                              //     -> Toutes les 1 seconde (60 frames),
         f[i].yRandom = random(-2.2f,2.2f);                    //        - Ce poisson change de vitesse au hasard sur l'axe y entre...
         if(f[i].yRandom >= 0 && f[i].yRandom < 1.5f){        //
           f[i].yRandom = 1.5f;                               //        ... 1.5 et 2.2...
         }                                                   //        ... et entre...
         if(f[i].yRandom < 0 && f[i].yRandom > -1.5f){        //
           f[i].yRandom = -1.5f;                              //        ... -2.2 et -1.5
         }                                                   //
         f[i].RandomFish = 0;                                //        - Reset au bout de 1 seconde le timer du changement de vitesse sur l'axe y    
      }                                                      //
      f[i].y =  f[i].y +  f[i].yRandom;                      //     -> Ce poisson bouge sur l'axe y avec la vitesse al\u00e9atoire sur l'axe y
      x = x - SpeedFishes;                                   //     -> Ce poisson avance sur l'axe x \u00e0 une certaine vitesse selon le niveau choisi
      image(GoldFish[frameCount%60],x,y);                    //     -> Permet d'afficher le poisson avec son animation
    }                                                        //
}

  
  
  
  
public void FishesMoveOnlyInTheRiver(){           // Fonction qui permet de g\u00e9rer les collisions des poissons
    
     f[i].ColorReadTopRight = get((int)(f[i].x + f[i].Width) , (int)(f[i].y - 1));                 // On prend la valeur rgb du pixel en haut \u00e0 droite de chaque poissons
     f[i].ColorReadTopLeft = get((int)(f[i].x - 1) , (int)(f[i].y - 1));                           // On prend la valeur rgb du pixel en haut \u00e0 gauche de chaque poissons
     f[i].ColorReadBottomRight = get((int)(f[i].x + f[i].Width) , (int)(f[i].y + f[i].Height));    // On prend la valeur rgb du pixel en bas \u00e0 droite de chaque poissons
     f[i].ColorReadBottomLeft = get((int)(f[i].x - 1) , (int)(f[i].y + f[i].Height));              // On prend la valeur rgb du pixel en bas \u00e0 gauche de chaque poissons
     
    
   if(f[i].GreenFishGrabed == false || f[i].YellowFishGrabed == false || f[i].RedFishGrabed == false || f[i].GoldFishGrabed == false){ // --> Tant que le poisson n'est pas "attrap\u00e9"
     if(f[i].ColorReadTopRight == color(34,41,122) || f[i].ColorReadTopLeft == color(34,41,122)){                                      //     -> Si le la haut du poisson d\u00e9tecte la couleur bleu fonc\u00e9
       f[i].y = f[i].y + 5;                                                                                                            //        - Le poisson descend de 5 pixels
       f[i].yRandom = -f[i].yRandom;                                                                                                   //        - Le poisson se d\u00e9place \u00e0 l'oppos\u00e9 sur l'axe y
       f[i].RandomFish = 0;                                                                                                            //        - Le timer du changement de vitesse sur l'axe y est reset
     }                                                                                                                                 //
     if(f[i].ColorReadBottomRight == color(34,41,122) || f[i].ColorReadBottomLeft == color(34,41,122)){                                //     -> Si le bas du poisson d\u00e9tecte la couleur bleue fonc\u00e9e
       f[i].y = f[i].y - 5;                                                                                                            //        - Le poisson monte de 5 pixels
       f[i].yRandom = -f[i].yRandom;                                                                                                   //        - Le poisson se d\u00e9place \u00e0 l'oppos\u00e9 sur l'axe y
       f[i].RandomFish = 0;                                                                                                            //        - Le timer du changement de vitesse sur l'axe y est reset
     }                                                                                                                                 //   
     if(f[i].ColorReadTopRight == color(64,64,64) || f[i].ColorReadTopLeft == color(64,64,64)             //     -> Si un des c\u00f4t\u00e9s du poisson d\u00e9t\u00e9cte une couleur autre que
       || f[i].ColorReadBottomRight == color(64,64,64) || f[i].ColorReadBottomLeft == color(64,64,64)     //        le bleu fonc\u00e9 ou le bleu clair...
       || f[i].ColorReadTopRight == color(61,61,53) || f[i].ColorReadTopLeft == color(61,61,53)           //        ...
       || f[i].ColorReadBottomRight == color(61,61,53) || f[i].ColorReadBottomLeft == color(61,61,53)     //        ...
       || f[i].ColorReadTopRight == color(87,220,31) || f[i].ColorReadTopLeft == color(87,220,31)         //        ...
       || f[i].ColorReadBottomRight == color(87,220,31) || f[i].ColorReadBottomLeft == color(87,220,53)   //        ...
       || f[i].ColorReadTopRight == color(64,64,64) || f[i].ColorReadTopLeft == color(64,64,64)           //        ...
       || f[i].ColorReadBottomRight == color(64,64,64) || f[i].ColorReadBottomLeft == color(64,64,64)     //        ...
       || f[i].ColorReadTopRight == color(61,61,53) || f[i].ColorReadTopLeft == color(61,61,53)           //        ...
       || f[i].ColorReadBottomRight == color(61,61,53) || f[i].ColorReadBottomLeft == color(61,61,53)     //        ...
       || f[i].ColorReadTopRight == color(87,220,31) || f[i].ColorReadTopLeft == color(87,220,31)         //        ...
       || f[i].ColorReadBottomRight == color(87,220,31) || f[i].ColorReadBottomLeft == color(87,220,53)   //        ...
       || f[i].ColorReadTopRight == color(0,177,34) || f[i].ColorReadTopLeft == color(0,177,34)           //        ...
       || f[i].ColorReadBottomRight == color(0,177,34) || f[i].ColorReadBottomLeft == color(0,177,34)){   //        ...
         f[i].x = -1000;                                                                                  //        - alors ce poisson est "mort", et donc part \u00e0 gauche 
     }                                                                                                    //          de l'\u00e9cran
   }                                                                                                      //
 }  
  
public void Pecher(){             // Fonction qui permet de g\u00e9rer la p\u00eache des poissons et les points marqu\u00e9s
    
    ColorReadByHmc = get((int)xHmc+3,(int)yHmc);       // --> On prend la couleur du pixel juste \u00e0 droite de l'hammecon
    
        
    //------------------------------------------------------------------------Pour p\u00eacher les poissons verts------------------------------------------------------------------------//
  
   if(ColorReadByHmc == color(30,205,12) || ColorReadByHmc == color(1,1,1)                 // --> Tant que l'hammecon touche un poisson vert, 
  || ColorReadByHmc == color(254,254,254) || ColorReadByHmc == color(21,137,8)){           //     (Pour d\u00e9tecter les collider des poissons verts avec l'hammecon)
     GreenFishCollider = true;                                                             //     -> alors il y a "collider"     
  }                                                                                        //                                                                   
  else {                                                                                   // --> Sinon,                                                                    
   GreenFishCollider = false;                                                              //     -> alors il n'y a pas "collider"                                                            
  }                                                                                        //
                                                                    
  if(GreenFishCollider == true && f[i].Color == color(30,205,12)                                         // --> Tant qu'il y a "collider", que CE poisson est vert, 
     && xHmc >= f[i].x && xHmc <= (f[i].x+f[i].Width) && yHmc >= f[i].y && yHmc <= (f[i].y+f[i].Height)  //     que CE poisson est proche de l'hammecon, (pour detecter le bon poisson),
     && mousePressed == true && EcartHmc != 0) {                                                         //     que l'on click, et que l'hammecon est dans l'eau (pour qu'il y ait qu'un poisson pech\u00e9 \u00e0 la fois),                                                   
    f[i].GreenFishGrabed = true;                                                                         //     -> alors CE poisson vert est "attrap\u00e9" !                                                                                                                                                                                                                                              
  }                                                                                                                                                                                                                                    
  
  if(f[i].GreenFishGrabed == true){                              // --> Tant que CE poisson est "attrap\u00e9", 
    GrabFishes();                                                //      -> La barre est activ\u00e9e
    f[i].x = xHmc - (f[i].Width/2);                              //      -> CE poisson est attach\u00e9 \u00e0 l'hammecon
    f[i].y = yHmc - (f[i].Height/2);                             //      
    if(yBarre >= 110  && GreenMouseReleased == true){            //      -> Si la barre est dans la zone verte et qu'on relache le click, 
                                                                 //          
      if(ComboGreenTimer >= 0  && ComboGreenTimer <= 60){        //          > Si le dernier poisson vert pech\u00e9 \u00e9tait entre 0 et 60s (5.6 \u00e0 6.6 s),
        score++;                                                 //            - le score augmente de 1 ! (score = +1 x1)
      }                                                          //
      if(ComboGreenTimer > 60 && ComboGreenTimer <= 120){        //          > Si le dernier poisson vert pech\u00e9 \u00e9tait entre 60+ et 120 (4.6 \u00e0 5.6 s), 
        score = score + 2;                                       //            - le score augmente de 2 ! (score = +1 x2)
        ComboScoreX2 = true;                                     //            - Active l'image "combo x2" 
        coombooo.trigger();                                      //            - le son "combooo" est activ\u00e9
      }                                                          //
      if(ComboGreenTimer > 120 && ComboGreenTimer <= 180){       //          > Si le dernier poisson vert pech\u00e9 \u00e9tait entre 120+ et 180 (3.6 \u00e0 4.6 s), 
        score = score + 3;                                       //            - le score augmente de 3 ! (score = +1 x3)
       ComboScoreX3 = true;                                      //            - Active l'image "combo x3"
       coombooo.trigger();                                       //            - le son "combooo" est activ\u00e9
      }                                                          //
      if(ComboGreenTimer > 180 && ComboGreenTimer <= 240){       //          > Si le dernier poisson vert pech\u00e9 \u00e9tait entre 180+ et 240 (2.6 \u00e0 3.6 s),
        score = score + 4;                                       //            - le score augmente de 4 ! (score = +1 x4)
        ComboScoreX4 = true;                                     //            - Active l'image "combo x4"
        coombooo.trigger();                                      //            - le son "combooo" est activ\u00e9
      }                                                          //
      if(ComboGreenTimer > 240 && ComboGreenTimer <= 400){       //          > Si le dernier poisson vert pech\u00e9 \u00e9tait entre 240+ et 400 (0 \u00e0 2.6 s),
        score = score + 5;                                       //            - le score augmente de 5 ! (score = +1 x5)
        ComboScoreX5 = true;                                     //            - Active l'image "combo x5"
        coombooo.trigger();                                      //            - le son "combooo" est activ\u00e9
      }                                                          //
      Score1 = true;                                             //          > Active l'image "score +1"
      ComboScoreTimer = 30;                                      //          > D\u00e9marre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence \u00e0 30 jusuqu'\u00e0 0 = 30 frames = 1/2 s)
      ComboGreenTimer = 400;                                     //          > D\u00e9marre le timer du combo pour les poisson verts (ComboGreenTimer commence \u00e0 400 jusqu'\u00e0 45 = 355 frames = 5.9166.. s)   
      succes.trigger();                                          //          > le son du succes est activ\u00e9
      fishingroad.trigger();                                     //          > le son du poisson qui sort de l'eau est activ\u00e9
      f[i].GreenFishGrabed = false;                              //          > CE poisson n'est plus "attach\u00e9"                                                                 
      f[i].x = -1000;                                            //          > On envoi CE poisson hors de la camera                                                                      
      GreenMouseReleased = false;                                //          > Permet que la barre de pr\u00e9cision se d\u00e9sactive
      FilTimer = 180;                                            //          > Permet de mettre l'hammecon dans l'eau, et donc de rep\u00e9cher                                                                       
    }                                                            //                                                                             
    if(yBarre < 110 && GreenMouseReleased == true){              //      -> Si la barre n'est pas dans la zone verte et qu'on relache le click,                                                                     
      score--;                                                   //          > Le score diminue de 1 !
      Scoremoins1 = true;                                        //          > Active l'image "score -1"
      fail.trigger();                                            //          > le son du fail est activ\u00e9
      ComboScoreTimer = 30;                                      //          > D\u00e9marre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence \u00e0 30 jusuqu'\u00e0 0 = 30 frames = 1/2 s)
      ComboGreenTimer = 45;                                      //          > Met le timer du combo pour les poisson verts \u00e0 l'initial ( ComboGreenTimer se remet \u00e0 45)             
      f[i].GreenFishGrabed = false;                              //          > CE poisson n'est plus "attach\u00e9"                                                                                                                                                                                              
      f[i].x = xHmc - 100;                                       //          > On envoi CE poisson un peu derri\u00e8re l'hammecon                                                                     
      GreenMouseReleased = false;                                //          > Permet que la barre de pr\u00e9cision se d\u00e9sactive
      FilTimer = 180;                                            //          > Permet de mettre l'hammecon dans l'eau, et donc de rep\u00e9cher                                                    
    }                                                            //                                                                                 
}                                                                //                                                                               



    //------------------------------------------------------------------------Pour p\u00eacher les poissons jaunes------------------------------------------------------------------------//
  
  if(ColorReadByHmc == color(255,225,8) || ColorReadByHmc == color(2,2,2)                  // --> Tant que l'hammecon touche un poisson jaune, 
  || ColorReadByHmc == color(253,253,253) || ColorReadByHmc == color(255,178,8)){          //     (Pour d\u00e9tecter les collider des poissons jaunes avec l'hammecon)
     YellowFishCollider = true;                                                            //     -> alors il y a "collider"     
  }                                                                                        //                                                                   
  else {                                                                                   // --> Sinon,                                                                    
   YellowFishCollider = false;                                                             //     -> alors il n'y a pas "collider"                                                            
  }                                                                                        //
                                                                    
  if(YellowFishCollider == true && f[i].Color == color(255,225,8)                                        // --> Tant qu'il y a "collider", que CE poisson est jaune, 
     && xHmc >= f[i].x && xHmc <= (f[i].x+f[i].Width) && yHmc >= f[i].y && yHmc <= (f[i].y+f[i].Height)  //     que CE poisson est proche de l'hammecon, (pour detecter le bon poisson),
     && mousePressed == true && EcartHmc != 0) {                                                         //     que l'on click, et que l'hammecon est dans l'eau (pour qu'il y ait qu'un poisson pech\u00e9 \u00e0 la fois),                                                   
    f[i].YellowFishGrabed = true;                                                                        //     -> alors CE poisson jaune est "attrap\u00e9" !                                                                                                                                                                                                                                             
  }                                                                                                                                                                                                                                    
  
  if(f[i].YellowFishGrabed == true){                              // --> Tant que CE poisson est "attrap\u00e9", 
    GrabFishes();                                                 //      -> La barre est activ\u00e9e
      f[i].x = xHmc - (f[i].Width/2);                             //      -> CE poisson est attach\u00e9 \u00e0 l'hammecon
    f[i].y = yHmc - (f[i].Height/2);                              //      
    if(yBarre >= 50 && yBarre < 110                               //      -> Si la barre est dans la zone jaune et qu'on relache le click,
    && YellowMouseReleased == true){                              //
                                                                  //          
      if(ComboYellowTimer >= 0  && ComboYellowTimer <= 60){       //          > Si le dernier poisson jaune pech\u00e9 \u00e9tait entre 0 et 60s (5.6 \u00e0 6.6 s),
        score = score + 2;                                        //            - le score augmente de 2 ! (score = +2 x1)
      }                                                           //
      if(ComboYellowTimer > 60 && ComboYellowTimer <= 120){       //          > Si le dernier poisson jaune pech\u00e9 \u00e9tait entre 60+ et 120 (4.6 \u00e0 5.6 s), 
        score = score + 4;                                        //            - le score augmente de 4 ! (score = +2 x4)
        ComboScoreX2 = true;                                      //            - Active l'image "combo x2" 
        coombooo.trigger();                                       //            - le son "combooo" est activ\u00e9
      }                                                           //
      if(ComboYellowTimer > 120 && ComboYellowTimer <= 180){      //          > Si le dernier poisson jaune pech\u00e9 \u00e9tait entre 120+ et 180 (3.6 \u00e0 4.6 s), 
        score = score + 6;                                        //            - le score augmente de 6 ! (score = +2 x3)
       ComboScoreX3 = true;                                       //            - Active l'image "combo x3"
       coombooo.trigger();                                        //            - le son "combooo" est activ\u00e9
      }                                                           //
      if(ComboYellowTimer > 180 && ComboYellowTimer <= 240){      //          > Si le dernier poisson jaune pech\u00e9 \u00e9tait entre 180+ et 240 (2.6 \u00e0 3.6 s),
        score = score + 8;                                        //            - le score augmente de 8 ! (score = +2 x4)
        ComboScoreX4 = true;                                      //            - Active l'image "combo x4"
        coombooo.trigger();                                       //            - le son "combooo" est activ\u00e9
      }                                                           //
      if(ComboYellowTimer > 240 && ComboYellowTimer <= 400){      //          > Si le dernier poisson jaune pech\u00e9 \u00e9tait entre 240+ et 400 (0 \u00e0 2.6 s),
        score = score + 10;                                       //            - le score augmente de 10 ! (score = +2 x5)
        ComboScoreX5 = true;                                      //            - Active l'image "combo x5"
        coombooo.trigger();                                       //            - le son "combooo" est activ\u00e9
      }                                                           //
      Score2 = true;                                              //          > Active l'image "score +2"
      ComboScoreTimer = 30;                                       //          > D\u00e9marre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence \u00e0 30 jusuqu'\u00e0 0 = 30 frames = 1/2 s)
      ComboYellowTimer = 400;                                     //          > D\u00e9marre le timer du combo pour les poisson jaune (ComboYellowTimer commence \u00e0 400 jusqu'\u00e0 45 = 355 frames = 5.9166.. s)   
      succes.trigger();                                           //          > le son du succes est activ\u00e9
      fishingroad.trigger();                                      //          > le son du poisson qui sort de l'eau est activ\u00e9
      f[i].YellowFishGrabed = false;                              //          > CE poisson n'est plus "attach\u00e9"                                                                 
      f[i].x = -1000;                                             //          > On envoi CE poisson hors de la camera                                                                      
      YellowMouseReleased = false;                                //          > Permet que la barre de pr\u00e9cision se d\u00e9sactive
      FilTimer = 180;                                             //          > Permet de mettre l'hammecon dans l'eau, et donc de rep\u00e9cher                                                                       
    }                                                             //                                                                             
    if((yBarre >= 110 && YellowMouseReleased == true)             //      -> Si la barre n'est pas dans la zone jaune et qu'on relache le click, 
    || (yBarre < 50 && YellowMouseReleased == true)){              //                                                                    
      score--;                                                    //          > Le score diminue de 1 !
      Scoremoins1 = true;                                         //          > Active l'image "score -1"
      fail.trigger();                                             //          > le son du fail est activ\u00e9
      ComboScoreTimer = 30;                                       //          > D\u00e9marre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence \u00e0 30 jusuqu'\u00e0 0 = 30 frames = 1/2 s)
      ComboYellowTimer = 45;                                      //          > Met le timer du combo pour les poisson jaune \u00e0 l'initial ( ComboYellowTimer se remet \u00e0 45)             
      f[i].YellowFishGrabed = false;                              //          > CE poisson n'est plus "attach\u00e9"                                                                                                                                                                                                
      f[i].x = xHmc - 100;                                        //          > On envoi CE poisson un peu derri\u00e8re l'hammecon                                                                     
      YellowMouseReleased = false;                                //          > Permet que la barre de pr\u00e9cision se d\u00e9sactive
      FilTimer = 180;                                             //          > Permet de mettre l'hammecon dans l'eau, et donc de rep\u00e9cher                                                    
    }                                                             //                                                                                 
}                                                                 //                                                                               



    //------------------------------------------------------------------------Pour p\u00eacher les poissons rouges------------------------------------------------------------------------//
  
   if(ColorReadByHmc == color(222,18,18) || ColorReadByHmc == color(3,3,3)                 // --> Tant que l'hammecon touche un poisson rouge, 
  || ColorReadByHmc == color(252,252,252) || ColorReadByHmc == color(175,15,15)){          //     (Pour d\u00e9tecter les collider des poissons rouges avec l'hammecon)
     RedFishCollider = true;                                                               //     -> alors il y a "collider"     
  }                                                                                        //                                                                   
  else {                                                                                   // --> Sinon,                                                                    
   RedFishCollider = false;                                                                //     -> alors il n'y a pas "collider"                                                            
  }                                                                                        //
                                                                    
  if(RedFishCollider == true && f[i].Color == color(222,18,18)                                           // --> Tant qu'il y a "collider", que CE poisson est rouge, 
     && xHmc >= f[i].x && xHmc <= (f[i].x+f[i].Width) && yHmc >= f[i].y && yHmc <= (f[i].y+f[i].Height)  //     que CE poisson est proche de l'hammecon, (pour detecter le bon poisson),
     && mousePressed == true && EcartHmc != 0) {                                                         //     que l'on click, et que l'hammecon est dans l'eau (pour qu'il y ait qu'un poisson pech\u00e9 \u00e0 la fois),                                                   
    f[i].RedFishGrabed = true;                                                                           //     -> alors CE poisson rouge est "attrap\u00e9" !                                                                                                                                                                                                                                             
  }                                                                                                                                                                                                                                    
  
  if(f[i].RedFishGrabed == true){                                // --> Tant que CE poisson est "attrap\u00e9", 
    GrabFishes();                                                //      -> La barre est activ\u00e9e
    f[i].x = xHmc - (f[i].Width/2);                              //      -> CE poisson est attach\u00e9 \u00e0 l'hammecon
    f[i].y = yHmc - (f[i].Height/2);                             //      
    if(yBarre >= 20 && yBarre < 50 && RedMouseReleased == true){ //      -> Si la barre est dans la zone rouge et qu'on relache le click, 
                                                                 //          
      if(ComboRedTimer >= 0  && ComboRedTimer <= 60){            //          > Si le dernier poisson rouge pech\u00e9 \u00e9tait entre 0 et 60s (5.6 \u00e0 6.6 s),
        score = score + 5;                                       //            - le score augmente de 5 ! (score = +5 x1)
      }                                                          //
      if(ComboRedTimer > 60 && ComboRedTimer <= 120){            //          > Si le dernier poisson rouge pech\u00e9 \u00e9tait entre 60+ et 120 (4.6 \u00e0 5.6 s), 
        score = score + 10;                                      //            - le score augmente de 10 ! (score = +5 x2)
        ComboScoreX2 = true;                                     //            - Active l'image "combo x2" 
        coombooo.trigger();                                      //            - le son "combooo" est activ\u00e9
      }                                                          //
      if(ComboRedTimer > 120 && ComboRedTimer <= 180){           //          > Si le dernier poisson rouge pech\u00e9 \u00e9tait entre 120+ et 180 (3.6 \u00e0 4.6 s), 
        score = score + 15;                                      //            - le score augmente de  15! (score = +5 x3)
       ComboScoreX3 = true;                                      //            - Active l'image "combo x3"
       coombooo.trigger();                                       //            - le son "combooo" est activ\u00e9
      }                                                          //
      if(ComboRedTimer > 180 && ComboRedTimer <= 240){           //          > Si le dernier poisson rouge pech\u00e9 \u00e9tait entre 180+ et 240 (2.6 \u00e0 3.6 s),
        score = score + 20;                                      //            - le score augmente de 20 ! (score = +5 x4)
        ComboScoreX4 = true;                                     //            - Active l'image "combo x4"
        coombooo.trigger();                                      //            - le son "combooo" est activ\u00e9
      }                                                          //
      if(ComboRedTimer > 240 && ComboRedTimer <= 400){           //          > Si le dernier poisson rouge pech\u00e9 \u00e9tait entre 240+ et 400 (0 \u00e0 2.6 s),
        score = score + 25;                                      //            - le score augmente de 25 ! (score = +5 x5)
        ComboScoreX5 = true;                                     //            - Active l'image "combo x5"
        coombooo.trigger();                                      //            - le son "combooo" est activ\u00e9
      }                                                          //
      Score4 = true;                                             //          > Active l'image "score +5"
      ComboScoreTimer = 30;                                      //          > D\u00e9marre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence \u00e0 30 jusuqu'\u00e0 0 = 30 frames = 1/2 s)
      ComboRedTimer = 400;                                       //          > D\u00e9marre le timer du combo pour les poisson rouges (ComboRedTimer commence \u00e0 400 jusqu'\u00e0 45 = 355 frames = 5.9166.. s)   
      succes.trigger();                                          //          > le son du succes est activ\u00e9
      fishingroad.trigger();                                     //          > le son du poisson qui sort de l'eau est activ\u00e9
      f[i].RedFishGrabed = false;                                //          > CE poisson n'est plus "attach\u00e9"                                                                 
      f[i].x = -1000;                                            //          > On envoi CE poisson hors de la camera                                                                      
      RedMouseReleased = false;                                  //          > Permet que la barre de pr\u00e9cision se d\u00e9sactive
      FilTimer = 180;                                            //          > Permet de mettre l'hammecon dans l'eau, et donc de rep\u00e9cher                                                                       
    }                                                            //                                                                             
    if((yBarre >= 50 && RedMouseReleased == true)                //      -> Si la barre n'est pas dans la zone rouge et qu'on relache le click, 
    ||(yBarre < 20 && RedMouseReleased == true)){                //                                                                    
      score--;                                                   //          > Le score diminue de 1 !
      Scoremoins1 = true;                                        //          > Active l'image "score -1"
      fail.trigger();                                            //          > le son du fail est activ\u00e9
      ComboScoreTimer = 30;                                      //          > D\u00e9marre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence \u00e0 30 jusuqu'\u00e0 0 = 30 frames = 1/2 s)
      ComboRedTimer = 45;                                        //          > Met le timer du combo pour les poisson rouges \u00e0 l'initial ( ComboRedTimer se remet \u00e0 45)             
      f[i].RedFishGrabed = false;                                //          > CE poisson n'est plus "attach\u00e9"                                                                                                                                                                                                 
      f[i].x = xHmc - 100;                                       //          > On envoi CE poisson un peu derri\u00e8re l'hammecon                                                                     
      RedMouseReleased = false;                                  //          > Permet que la barre de pr\u00e9cision se d\u00e9sactive
      FilTimer = 180;                                            //          > Permet de mettre l'hammecon dans l'eau, et donc de rep\u00e9cher                                                    
    }                                                            //                                                                                 
}                                                                //                                                                               

    //------------------------------------------------------------------------Pour p\u00eacher les poissons dor\u00e9s------------------------------------------------------------------------//
  
   if(ColorReadByHmc == color(212,175,55) || ColorReadByHmc == color(4,4,4)                // --> Tant que l'hammecon touche un poisson dor\u00e9, 
  || ColorReadByHmc == color(251,251,251) || ColorReadByHmc == color(188,153,49)){         //     (Pour d\u00e9tecter les collider des poissons dor\u00e9 avec l'hammecon)
     GoldFishCollider = true;                                                              //     -> alors il y a "collider"     
  }                                                                                        //                                                                   
  else {                                                                                   // --> Sinon,                                                                    
   GoldFishCollider = false;                                                               //     -> alors il n'y a pas "collider"                                                            
  }                                                                                        //
                                                                    
  if(GoldFishCollider == true && f[i].Color == color(212,175,55)                                         // --> Tant qu'il y a "collider", que CE poisson est dor\u00e9, 
     && xHmc >= f[i].x && xHmc <= (f[i].x+f[i].Width) && yHmc >= f[i].y && yHmc <= (f[i].y+f[i].Height)  //     que CE poisson est proche de l'hammecon, (pour detecter le bon poisson),
     && mousePressed == true && EcartHmc != 0) {                                                         //     que l'on click, et que l'hammecon est dans l'eau (pour qu'il y ait qu'un poisson pech\u00e9 \u00e0 la fois),                                                   
    f[i].GoldFishGrabed = true;                                                                          //     -> alors CE poisson dor\u00e9 est "attrap\u00e9" !                                                                                                                                                                                                                                              
  }                                                                                                                                                                                                                                    
  
  if(f[i].GoldFishGrabed == true){                               // --> Tant que CE poisson est "attrap\u00e9", 
    GrabFishes();                                                //      -> La barre est activ\u00e9e
    f[i].x = xHmc - (f[i].Width/2);                              //      -> CE poisson est attach\u00e9 \u00e0 l'hammecon
    f[i].y = yHmc - (f[i].Height/2);                             //      
    if(yBarre >= 5 && yBarre < 20 && GoldMouseReleased == true){ //      -> Si la barre est dans la zone dor\u00e9 et qu'on relache le click, 
                                                                 //          
      if(ComboGoldTimer >= 0  && ComboGoldTimer <= 60){          //          > Si le dernier poisson dor\u00e9 pech\u00e9 \u00e9tait entre 0 et 60s (5.6 \u00e0 6.6 s),
        score = score + 15;                                      //            - le score augmente de 15 ! (score = +15 x1)
      }                                                          //
      if(ComboGoldTimer > 60 && ComboGoldTimer <= 120){          //          > Si le dernier poisson dor\u00e9 pech\u00e9 \u00e9tait entre 60+ et 120 (4.6 \u00e0 5.6 s), 
        score = score + 30;                                      //            - le score augmente de 30 ! (score = +15 x2)
        ComboScoreX2 = true;                                     //            - Active l'image "combo x2" 
        coombooo.trigger();                                      //            - le son "combooo" est activ\u00e9
      }                                                          //
      if(ComboGoldTimer > 120 && ComboGoldTimer <= 180){         //          > Si le dernier poisson dor\u00e9 pech\u00e9 \u00e9tait entre 120+ et 180 (3.6 \u00e0 4.6 s), 
        score = score + 45;                                      //            - le score augmente de  45! (score = +15 x3)
       ComboScoreX3 = true;                                      //            - Active l'image "combo x3"
       coombooo.trigger();                                       //            - le son "combooo" est activ\u00e9
      }                                                          //
      if(ComboGoldTimer > 180 && ComboGoldTimer <= 240){         //          > Si le dernier poisson dor\u00e9 pech\u00e9 \u00e9tait entre 180+ et 240 (2.6 \u00e0 3.6 s),
        score = score + 60;                                      //            - le score augmente de 60 ! (score = +15 x4)
        ComboScoreX4 = true;                                     //            - Active l'image "combo x4"
        coombooo.trigger();                                      //            - le son "combooo" est activ\u00e9
      }                                                          //
      if(ComboGoldTimer > 240 && ComboGoldTimer <= 400){         //          > Si le dernier poisson dor\u00e9 pech\u00e9 \u00e9tait entre 240+ et 400 (0 \u00e0 2.6 s),
        score = score + 75;                                      //            - le score augmente de 75 ! (score = +15 x5)
        ComboScoreX5 = true;                                     //            - Active l'image "combo x5"
        coombooo.trigger();                                      //            - le son "combooo" est activ\u00e9
      }                                                          //
      Score8 = true;                                             //          > Active l'image "score +15"
      ComboScoreTimer = 30;                                      //          > D\u00e9marre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence \u00e0 30 jusuqu'\u00e0 0 = 30 frames = 1/2 s)
      ComboGoldTimer = 400;                                      //          > D\u00e9marre le timer du combo pour les poisson dor\u00e9 (ComboGoldTimer commence \u00e0 400 jusqu'\u00e0 45 = 355 frames = 5.9166.. s)   
      succes.trigger();                                          //          > le son du succes est activ\u00e9
      fishingroad.trigger();                                     //          > le son du poisson qui sort de l'eau est activ\u00e9
      f[i].GoldFishGrabed = false;                               //          > CE poisson n'est plus "attach\u00e9"                                                                 
      f[i].x = -1000;                                            //          > On envoi CE poisson hors de la camera                                                                      
      GoldMouseReleased = false;                                 //          > Permet que la barre de pr\u00e9cision se d\u00e9sactive
      FilTimer = 180;                                            //          > Permet de mettre l'hammecon dans l'eau, et donc de rep\u00e9cher                                                                       
    }                                                            //                                                                             
    if((yBarre >= 20 && GoldMouseReleased == true)               //      -> Si la barre n'est pas dans la zone dor\u00e9 et qu'on relache le click, 
    ||(yBarre < 5 && GoldMouseReleased == true)){                //                                                                    
      score--;                                                   //          > Le score diminue de 1 !
      Scoremoins1 = true;                                        //          > Active l'image "score -1"
      fail.trigger();                                            //          > le son du fail est activ\u00e9
      ComboScoreTimer = 30;                                      //          > D\u00e9marre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence \u00e0 30 jusuqu'\u00e0 0 = 30 frames = 1/2 s)
      ComboGoldTimer = 45;                                       //          > Met le timer du combo pour les poisson dor\u00e9 \u00e0 l'initial ( ComboGoldTimer se remet \u00e0 45)             
      f[i].GoldFishGrabed = false;                               //          > CE poisson n'est plus "attach\u00e9"                                                                                                                                                                                              
      f[i].x = xHmc - 100;                                       //          > On envoi CE poisson un peu derri\u00e8re l'hammecon                                                                     
      GoldMouseReleased = false;                                 //          > Permet que la barre de pr\u00e9cision se d\u00e9sactive
      FilTimer = 180;                                            //          > Permet de mettre l'hammecon dans l'eau, et donc de rep\u00e9cher                                                    
    }                                                            //                                                                                 
  }                                                              //                                                                               
 }

}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public void mouseReleased(){                // Fonction pour d\u00e9tecter quand le click de la souris est relach\u00e9e
  
  MouseReleased = true;
  
  if(GreenFishCollider == true ){
    GreenMouseReleased = true;
  }
  if(YellowFishCollider == true ){
    YellowMouseReleased = true;
  }
  if(RedFishCollider == true ){
    RedMouseReleased = true;
  }
  if(GoldFishCollider == true ){
    GoldMouseReleased = true;
  }
  
}
  
  
public void InvokeFishes() {             // Fonction pour le programme de tous les poissons (object Fishes)
  
    for(i=0;i<=99;i++){
       f[i].DisplayFish();                // Affiche les poissons 
       f[i].FishesMoveOnlyInTheRiver();   // poissons restent sur la rivi\u00e8re (collisions des poissons)
       f[i].Pecher();                     // gestion de la p\u00eache des poissons et des points marqu\u00e9s
       
        
    }
      
   
  
}

float xBarre;  // Coordonn\u00e9e x de la barre
float yBarre;  // Coordonn\u00e9e y de la barre

public void GrabFishes() {    // Fonction pour permettre d'afficher la barre de jauge 
  
 fill(61,61,53,150);         // Rectangle semi transparent
 rect(80,0,235,250);         // en fond
  
  strokeJoin(ROUND);         // Contour arrondi
  stroke(0xff636363);           // Couleur des contours
  strokeWeight(2);           // Epaisseur des contours
  
  fill(30,205,12);           // Couleur verte 
  rect(100,110,200,120);     // Zone verte
  
  fill(255,225,8);           // Couleur jaune      // cible
  rect(100,50,200,60);       // Zone jaune
     
  fill(222,18,18);           // Couleur Rouge       // cible
  rect(100,20,200,30);       // Zone rouge
  
  fill(212,175,55);          // Couleur dor\u00e9
  rect(100,5,200,15);        // Zone dor\u00e9
  
  
  
  xBarre = 200;          // Position de
  yBarre = yBarre - 3;   // la barre
  if(yBarre < 5){        //
    yBarre = 230;        //
  }
  
  strokeWeight(1);                  // Eppaisseur du contour de la barre
  fill(0,0,0);                      // Couleur noir de la barre
  ellipse(xBarre,yBarre,250,2);     // barre
  
}



public void ComboScore(){       // Fonction pour permettre d'afficher les barre de jauge "Combo"
  
  
   if(ComboGreenTimer >= 45){                       // Permet de faire descendre la barre de
      ComboGreenTimer--;                            // jauge du combo des poissons verts
   }                                                //
   if(ComboYellowTimer >= 45){                      // Permet de faire descendre la barre de
      ComboYellowTimer = ComboYellowTimer - 0.75f;   // jauge du combo des poissons jaunes
   }                                                //
   if(ComboRedTimer >= 45){                         // Permet de faire descendre la barre de
      ComboRedTimer = ComboRedTimer - 0.50f;         // jauge du combo des poissons rouges
   }                                                //
   if(ComboGoldTimer >= 45){                        // Permet de faire descendre la barre de
      ComboGoldTimer = ComboGoldTimer - 0.25f;       // jauge du combo des poissons dor\u00e9s
   }                                                //
    
if(ComboScoreTimer >= 1){             // Timer pour afficher les images score et combo pendant 1/2 s
      ComboScoreTimer--;              // (ComboScoreTimer commence \u00e0 30 jusuqu'\u00e0 0 = 30 frames = 1/2 s)
}                                     //
   
    if(ComboScoreTimer == 0){             // A la fin du timer pour afficher les scores et combo, 
      ComboScoreX2 = false;               //  > l'image "X2" n'est plus affich\u00e9e
      ComboScoreX3 = false;               //  > l'image "X3" n'est plus affich\u00e9e
      ComboScoreX4 = false;               //  > l'image "X4" n'est plus affich\u00e9e
      ComboScoreX5 = false;               //  > l'image "X5" n'est plus affich\u00e9e
      Score1 = false;                     //  > l'image "+1" n'est plus affich\u00e9e
      Score2 = false;                     //  > l'image "+2" n'est plus affich\u00e9e
      Score4 = false;                     //  > l'image "+5" n'est plus affich\u00e9e
      Score8 = false;                     //  > l'image "+15" n'est plus affich\u00e9e
      Scoremoins1 = false;                //  > l'image "-1" n'est plus affich\u00e9e
      ComboImg = loadImage("Combo0.png"); //  > l'image des combos est transparente
      ScoreImg = loadImage("Score0.png"); //  > l'image des scores est transparente
    }                                     //
    
    fill(61,61,53,150);                       //
     if(ComboScoreX2 == true){                // Si l'image "X2" est activ\u00e9,
      rect(580,80,300,120);                   // -> Le fond semi transparent appara\u00eet
      ComboImg = loadImage("Combox2.png");    // -> l'image "X2" est affich\u00e9e
    }                                         //
     if(ComboScoreX3 == true){                // Si l'image "X3" est activ\u00e9,
      rect(580,80,300,120);                   // -> Le fond semi transparent appara\u00eet
      ComboImg = loadImage("Combox3.png");    // -> l'image "X3" est affich\u00e9e
    }                                         //
     if(ComboScoreX4 == true){                // Si l'image "X4" est activ\u00e9,
      rect(580,80,300,120);                   // -> Le fond semi transparent appara\u00eet
      ComboImg = loadImage("Combox4.png");    // -> l'image "X4" est affich\u00e9e
    }                                         //
     if(ComboScoreX5 == true){                // Si l'image "X5" est activ\u00e9,
      rect(580,80,300,120);                   // -> Le fond semi transparent appara\u00eet
      ComboImg = loadImage("Combox5.png");    // -> l'image "X5" est affich\u00e9e
    }                                         //
    if(Score1 == true){                       // Si l'image "+1" est activ\u00e9,
      rect(580,80,300,120);                   // -> Le fond semi transparent appara\u00eet
      ComboImg = loadImage("score+1.png");    // -> l'image "+1" est affich\u00e9e
    }                                         //
     if(Score2 == true){                      // Si l'image "+2" est activ\u00e9,
      rect(580,80,300,120);                   // -> Le fond semi transparent appara\u00eet
      ComboImg = loadImage("score+2.png");    // -> l'image "+2" est affich\u00e9e
    }                                         //
     if(Score4 == true){                      // Si l'image "+5" est activ\u00e9,
      rect(580,80,300,120);                   // -> Le fond semi transparent appara\u00eet
      ComboImg = loadImage("score+4.png");    // -> l'image "+5" est affich\u00e9e
    }                                         //
     if(Score8 == true){                      // Si l'image "+15" est activ\u00e9,
      rect(580,80,300,120);                   // -> Le fond semi transparent appara\u00eet
      ComboImg = loadImage("score+8.png");    // -> l'image "+15" est affich\u00e9e
    }                                         //
     if(Scoremoins1 == true){                 // Si l'image "-1" est activ\u00e9,
      rect(580,80,300,120);                   // -> Le fond semi transparent appara\u00eet
      ComboImg = loadImage("score-1.png");    // -> l'image "-1" est affich\u00e9e
    }                                         //
    

    image(ScoreImg,575, 40);        // Afficher les images des scores
    image(ComboImg,700,40);         // Afficher les images des combos
    
    
    
    
    
//--------------------------------------------------Affiche les barre de jauge des combos--------------------------------------------------//

    strokeWeight(0);       // Epaisseur des contours
    fill(227,232,190);
    rect(1175,5,200,215);
    
    strokeJoin(ROUND);     // Contour arrondi
    stroke(0xff636363);       // Couleur des contours
    
    
    fill(30,205,12);
    rect(1200,10,30,(ComboGreenTimer/2)+1);
    fill(255,225,8);
    rect(1240,10,30,(ComboYellowTimer/2)+1);
    fill(222,18,18);
    rect(1280,10,30,(ComboRedTimer/2)+1);
    fill(212,175,55);
    rect(1320,10,30,(ComboGoldTimer/2)+1);
    
    stroke(0xff636363);      // Couleur des contours
    strokeWeight(2);      // Epaisseur des contours
    fill(0,0,0,0);
    rect(1200,10,30,201);
    rect(1240,10,30,201);
    rect(1280,10,30,201);
    rect(1320,10,30,201);
    
    stroke(0,0,0);        // Couleur des contours
    strokeWeight(1);      // Epaisseur des contours
    fill(0,0,0);
    rect(1180,41,190,0);
    rect(1180,71,190,0);
    rect(1180,101,190,0);
    rect(1180,131,190,0);
    rect(1180,211,190,0);
    
    textSize(13);          // Taille de la police
    text("X2",1181,60);
    text("X2",1355,60);
    text("X3",1181,90);
    text("X3",1355,90);
    text("X4",1181,120);
    text("X4",1355,120);
    text("X5",1181,170);
    text("X5",1355,170);
}
//------------------------------------------------------------------------------------------------------------------------------------------//

int RestartMenu;
float xRestart;
float yRestart;
float widthRestart;
float heightRestart;
float textSizeRestart;
float xtextRestart;
float ytextRestart;
int MainBackMenu;
float xMainBack;
float yMainBack;
float widthMainBack;
float heightMainBack;
float textSizeMainBack;
float xtextMainBack;
float ytextMainBack;

//-----------------------------------------Fonction pour afficher que vous avez perdu-----------------------------------------//
public void Scenedeath(){
  
  image(fondecranflou,0,0);
 
  
  fill(210,0,0);
  textSize(100);
  text("VOUS AVEZ PERDU...",250,200);
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
int JouerMenu;
float xJouer;
float yJouer;
float widthJouer;
float heightJouer;
float textSizeJouer = 100;
float xtextJouer;
float ytextJouer;
int QuitMenu;
float xQuit;
float yQuit;
float widthQuit;
float heightQuit;
float textSizeQuit;
float xtextQuit;
float ytextQuit;

int FacileMenu;
float xFacile;
float yFacile;
float widthFacile;
float heightFacile;
float textSizeFacile;
float xtextFacile;
float ytextFacile;
int NormalMenu;
float xNormal;
float yNormal;
float widthNormal;
float heightNormal;
float textSizeNormal;
float xtextNormal;
float ytextNormal;
int DifficileMenu;
float xDifficile;
float yDifficile;
float widthDifficile;
float heightDifficile;
float textSizeDifficile;
float xtextDifficile;
float ytextDifficile;

int BackMenu;
float xBack;
float yBack;
float widthBack;
float heightBack;
float textSizeBack;
float xtextBack;
float ytextBack;

//----------------------------------------Boutons "jouer" et "quitter"----------------------------------------//
public void Scene0(){
  
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
  public void Scene10(){
  
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
    fond = loadImage("background1.jpg");    // T\u00e9l\u00e9charge l'image du background
    SpeedFishes = 1.1f;
    scene = 1;
    MouseReleased = false;
  }
  if(MouseReleased == true && NormalMenu == color(75,75,75)){
    ResetBool = true;
    fond = loadImage("background2.jpg");    // T\u00e9l\u00e9charge l'image du background
    SpeedFishes = 1.4f;
    scene = 1;
    MouseReleased = false;
  }
  if(MouseReleased == true && DifficileMenu == color(75,75,75)){
    ResetBool = true;
    fond = loadImage("background3.jpg");    // T\u00e9l\u00e9charge l'image du background
    SpeedFishes = 1.9f;
    scene = 1;
    MouseReleased = false;
  }
  if(MouseReleased == true && BackMenu == color(75,75,75)){
    scene = 0;
    MouseReleased = false;
  }
 }
//-----------------------------------------------------------------------------------------------------// 

//-----------------------------------------Fonction pour afficher que vous avez gagn\u00e9-----------------------------------------//
public void Scene2() {
  
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
int ColorLeftBoat;           // Couleur qui prendra la valeur rgb du pixel en bas \u00e0 gauche du bateau
int ColorMidBoat;            // Couleur qui prendra la valeur rgb du pixel en bas au milieu du bateau
int ColorRightBoat;          // Couleur qui prendra la valeur rgb du pixel en bas \u00e0 droite du bateau
int ColorReadByTopBoat;      // Couleur qui prendra la valeur rgb du pixel en haut \u00e0 gauche du bateau
int ColorReadByBottomBoat;   // Couleur qui prendra la valeur rgb du pixel en bas \u00e0 gauche du bateau

int score;    // Score


public void keyPressed(){                            //
  if(key==CODED){                             //
    if(keyCode==DOWN) yBoat = yBoat + 5 ;     // Fait d\u00e9placer le bateau vers le bas \u00e0 une vitesse de 5
    if(keyCode==UP) yBoat = yBoat - 5 ;       // Fait d\u00e9placer le bateau vers le haut \u00e0 une vitesse de 5
  }                                           //
}                                             //

public void textsAndBackground() {     // Fonction pour afficher le score, la distance parcouru, le background...
  
  xFond = xFond - 1;      // Background qui bouge vers
  image(fond,xFond,0);    // la gauche
  
  stroke(0,0,0);
  strokeWeight(1);
  strokeJoin(ROUND);
  fill(61,61,53,150);
  rect(1150,0,250,240);
  rect(900,0,250,120);
  rect(470,0,430,80);
  
  fill(0,200,23);
  textSize(30);
  text("score : " + score, 920, 40);
   
if(scene == 1){
  textSize(30);
  text("Distance : " + round(xBoatPourcent) + " %", 920, 100);  
  if(xBoatPourcent >= 100){
    ResetBool = true;
    scene = 2;
  }
}   

}


public void BoatMoveOnlyInTheRiver(){        // Fonction qui permet de g\u00e9rer les collisions du bateau
 
  ColorReadByTopBoat = get((int)xBoat - 1, (int)yBoat + 80);          // On prend la valeur rgb du pixel en haut \u00e0 gauche du bateau
  ColorReadByBottomBoat = get((int)xBoat - 1, (int)yBoat + 130);      // On prend la valeur rgb du pixel en bas \u00e0 gauche du bateau
  ColorLeftBoat = get((int)xBoat , (int)yBoat + 110);                 // On prend la valeur rgb du pixel en bas \u00e0 gauche du bateau
  ColorMidBoat = get((int)xBoat + 102 , (int)yBoat + 110);            // On prend la valeur rgb du pixel en bas au milieu du bateau
  ColorRightBoat = get((int)xBoat + 155, (int)yBoat + 110);           // On prend la valeur rgb du pixel en bas \u00e0 droite du bateau
  
  
  if(ColorReadByTopBoat == color(87,220,31)){         // Si le haut du bateau d\u00e9tecte le vert de l'herbe
   yBoat = yBoat + 5;                                 // -> Le bateau se d\u00e9place vers le bas                    
  }                                                   //
  if(ColorReadByBottomBoat == color(87,220,31)){      // Si le bas du bateau d\u00e9tecte le vert de l'herbe                
   yBoat = yBoat - 5;                                 // -> Le bateau se d\u00e9place vers le haut    
  }                                                   //
  
  if(ColorLeftBoat == color(64,64,64) || ColorMidBoat == color(64,64,64)     // Si le bas du bateau d\u00e9tecte la couleur du rocher 
     || ColorRightBoat == color(64,64,64)){                                  //
    scene = 666;                                                             // -> Vous \u00eates mort, et la sc\u00e8ne qui indique que
  }                                                                          //    vous \u00eates mort appara\u00eet
}

public void Hmc_Boat_Fil(){       // Fonctiion pour appara\u00eetre le bateau, le fil et l'hammecon
  
   if(mousePressed == true){                        // Quand on click sur la souris,  
     EcartHmc = 0;                                  // -> La canne \u00e0 p\u00eache est lev\u00e9e
     FilTimer = 0;                                  // 
  }          
                                                        
  if(EcartHmc == 0){                // D\u00e9mare le timer (barre rose en haut de l'\u00e9cran)
    FilTimer++;                     // pour pouvoir rep\u00eacher
  }
  if(EcartHmc == 50){               // Si on peut p\u00eacher
    FilTimer = 180;                 // -> la barre rose est rempli enti\u00e8rement
  }  
                                                                     
  if(FilTimer > 180 && mousePressed == false){          // Si la barre rose est rempli enti\u00e8rement  
    yBarre = 230;                                       // -> La barre noir est reset
    EcartHmc = 50;                                      // -> la canne \u00e0 p\u00eache peut p\u00eacher
    FilTimer = 180;                                     // -> Pour ne pas d\u00e9passer la barre rose           
  }                                                     //
  
  fill(221,0,192);        
  strokeWeight(0);
  rect(500, 10, FilTimer*2 + 10, 50);     //Affichage des la barre rose en haut de l'\u00e9cran
  fill(0,0,0,0);
  strokeWeight(3);
  rect(500,10,370,50);
  
  xHmc = xFil + 70;               // Position x et y 
  yHmc = yFil + EcartHmc;         // de l'hammecon
  
  xFil = xBoat + 180;             // Position x et y 
  yFil = yBoat + 70;              // du fil
  
 stroke(255);
 strokeWeight(1);
 fill(0, 0, 0);
 line(xFil, yFil, xHmc, yHmc);                     // Affichage du fil
 ellipse(xHmc, yHmc, 5, 5);                        // Affichage de l'hammecon
 image(boat[frameCount%60], xBoat, yBoat);         // Affichage du bateau avec son animation
}


public void reset() {   // Fonction pour reset toutes les variables importantes

  if(ResetBool == true){
    xHmc = 0;
    yHmc = 0;
    xBoat = 150;
    yBoat = 411;
    EcartHmc = 50;
    xFond = -6800;
    score = 0;  
    DisplayFishTimer = 100;    
    j = 0;
    i =0;
    GreenFishCollider = false;
    YellowFishCollider = false;
    RedFishCollider = false;
    GoldFishCollider = false;
    GreenMouseReleased = false;
    YellowMouseReleased = false;
    RedMouseReleased = false;
    GoldMouseReleased = false;
    ComboGreenTimer = 45;
    ComboRedTimer = 45;
    ComboYellowTimer = 45;
    ComboGoldTimer = 45;
    ComboScoreTimer = 0;
    ComboScoreX2 = false;
    ComboScoreX3 = false;
 ComboScoreX4 = false;
 ComboScoreX5 = false;
 Score1 = false;
 Score2 = false;
 Score4 = false;
 Score8 = false;
 Scoremoins1 = false;
 CreateAllFishesBool = true;
 yBarre = 230;
    
    ResetBool = false;
    
    
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#000000", "--stop-color=#cccccc", "Fisheboat" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
