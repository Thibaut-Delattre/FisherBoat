int i;                                   // Index du tableau des poissons
int j;                                   // Index pour faire apparaître les poissons toutes les 100 frames
float SpeedFishes;                       // Vitesse sur l'axe x des poissons
color ColorReadByHmc;                    // Couleur qui prendra la valeur rgb du pixel à droite de l'hammecon
float DisplayFishTimer;                  // Timer pour afficher un poisson toutes les 100 frames
int RandomColors;                        // Index pour la randomisation des couleurs des poissons

boolean GreenFishCollider = false;       // Le hammecon touche un poisson vert ?
boolean YellowFishCollider = false;      // Le hammecon touche un poisson jaune ?
boolean RedFishCollider = false;         // Le hammecon touche un poisson rouge ?
boolean GoldFishCollider = false;        // Le hammecon touche un poisson doré ?

boolean GreenMouseReleased;              // Le click est realché pour un poisson vert ?
boolean YellowMouseReleased;             // Le click est realché pour un poisson jaune ?
boolean RedMouseReleased;                // Le click est realché pour un poisson rouge ?
boolean GoldMouseReleased;               // Le click est realché pour un poisson doré ?
boolean MouseReleased = false;           // Le click est relaché ?

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
boolean CreateAllFishesBool;             // Pour permettre de créer qu'une seule fois les 100 poissons

Fishes[] f = new Fishes[100];   // On créé un tableau de 100 poissons

int ColorsR[] = {30,30,30,30,30,30,30,30,30,255,255,255,255,255,255,222,222,222,212};          // 50 % Vert ; 30 % Jaune ;
int ColorsG[] = {205,205,205,205,205,205,205,205,205,225,225,225,225,225,225,18,18,18,175};    // 15 % rouge ; 5 % Doré
int ColorsB[] = {12,12,12,12,12,12,12,12,12,8,8,8,8,8,8,18,18,18,55};                          //


void FishEverySteps(){            // Fonction pour faire apparaître toutes les 100 frames un poisson 
  
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

void CreateAllFishes(){       // Fonction pour créer les 100 poissons
  
  if(CreateAllFishesBool == true){
     for(i=0;i<=99;i++){
        RandomColors = (int)random(0, 19);
        f[i] = new Fishes(xBoat + (-500), 500, 50, 50, random(-1,1), random(0,120), color(ColorsR[RandomColors], ColorsG[RandomColors],
        ColorsB[RandomColors]), false, false, false, false, color(235,72,47), color(235,72,47), color(235,72,47), color(235,72,47));     
        CreateAllFishesBool = false;        // Pour que les poissons soient créés qu'une seule fois
     }
   }
}


//--------------------------------------POISSON CLASS--------------------------------------//

class Fishes {          // On créé un nouvel object : les poissons
  
//------------------------------------DATA------------------------------------//
  float x;                             // Coordonné x des poissons       
  float y;                             // Coordonné y des poissons                                  
  float Width;                         // Longueur des poissons
  float Height;                        // Hauteur des poissons
  float yRandom;                       // Vitesse aléatoire sur l'axe y des poissons
  float RandomFish;                    // Intervalle des changememts de yRandom
  color Color;                         // Couleur des poissons
  boolean GreenFishGrabed = false;     // Poisson vert attrapé
  boolean YellowFishGrabed = false;    // Poisson jaune attrapé
  boolean RedFishGrabed = false;       // Poisson rouge attrapé
  boolean GoldFishGrabed = false;      // Poisson doré attrapé
  color ColorReadTopRight;             // Couleur du pixel en haut à droite du poisson
  color ColorReadTopLeft;              // Couleur du pixel en haut à gauche du poisson
  color ColorReadBottomRight;          // Couleur du pixel en bas à droite du poisson
  color ColorReadBottomLeft;           // Couleur du pixel en bas à gauche du poisson
//----------------------------------------------------------------------------//
  
  Fishes(){}        // --> Constructeur vide

//---------------------------------------------------------------------------------------Constructeur---------------------------------------------------------------------------------------//
  Fishes(float newxFish, float newyFish, float newWidth, float newHeight, float newyRandom, float newRandomFish, color newColor, boolean newGreenFishGrabed, boolean newYellowFishGrabed,    
  boolean newRedFishGrabed, boolean newGoldFishGrabed, color newColorReadTopRight, color newColorReadTopLeft, color newColorReadBottomRight, color newColorReadBottomLeft){
    
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
  
  void DisplayFishEverySteps(){    // Fonction qui permet d'afficher les poissons toutes les 100 frames à droite de l'écran

  if(i<=99){                      // Pour les 100 poissons créés, chacun sont déplacés toutes les 100 frames (voir "void FishEverySteps(){}")
    f[j].x = xBoat + 1340;        // à droite de l'écran 
  }  
}

  void DisplayFish(){        // Fonction qui pemet d'afficher les poissons avec leurs principales caractéristiques
    
    f[i].RandomFish ++;      // Timer du changement de vitesse sur l'axe y
    
    if(f[i].Color == color(30,205,12)) {                     // --> Si le poisson est vert
      Width = 107;                                           //     -> Sa largeur est de 107 pixels       
      Height = 74;                                           //     -> Sa hauteur est de 74 pixels
      if(f[i].RandomFish >= 120){                            //     -> Toutes les 2 secondes (120 frames), 
         f[i].yRandom = random(-1,1);                        //        - Ce poisson change de vitesse au hasard sur l'axe y entre -1 et 1
         f[i].RandomFish = 0;                                //        - Reset au bout de 2 secondes le timer du changement de vitesse sur l'axe y
      }                                                      //
      f[i].y =  f[i].y +  f[i].yRandom;                      //     -> Ce poisson bouge sur l'axe y avec la vitesse aléatoire sur l'axe y
      x = x - SpeedFishes;                                   //     -> Ce poisson avance sur l'axe x à une certaine vitesse selon le niveau choisi   
      image(GreenFish[frameCount%60],x,y);                   //     -> Permet d'afficher le poisson avec son animation
    }                                                        //
    
    
     if(f[i].Color == color(255,225,8)) {                    // --> Si le poisson est jaune
      Width = 79;                                            //     -> Sa largeur est de 79 pixels
      Height = 63;                                           //     -> Sa hauteur est de 63 pixels
     if(f[i].RandomFish >= 90){                              //     -> Toutes les 1.5 secondes (90 frames),
         f[i].yRandom = random(-1.5,1.5);                    //        - Ce poisson change de vitesse au hasard sur l'axe y entre...
         if(f[i].yRandom >= 0 && f[i].yRandom < 0.5){        //
           f[i].yRandom = 0.5;                               //        ... 0.5 et 1.5...
         }                                                   //        ... et entre...
         if(f[i].yRandom < 0 && f[i].yRandom > -0.5){        //
           f[i].yRandom = -0.5;                              //        ... -1.5 et -0.5
         }                                                   //
         f[i].RandomFish = 0;                                //        - Reset au bout de 1.5 secondes le timer du changement de vitesse sur l'axe y    
      }                                                      //
      f[i].y =  f[i].y +  f[i].yRandom;                      //     -> Ce poisson bouge sur l'axe y avec la vitesse aléatoire sur l'axe y
      x = x - SpeedFishes;                                   //     -> Ce poisson avance sur l'axe x à une certaine vitesse selon le niveau choisi
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
      f[i].y =  f[i].y +  f[i].yRandom;                      //     -> Ce poisson bouge sur l'axe y avec la vitesse aléatoire sur l'axe y
      x = x - SpeedFishes;                                   //     -> Ce poisson avance sur l'axe x à une certaine vitesse selon le niveau choisi
      image(RedFish[frameCount%60],x,y);                     //     -> Permet d'afficher le poisson avec son animation
    }                                                        //
    
    
     if(f[i].Color == color(212,175,55)) {                   // --> Si le poisson est doré
      Width = 52;                                            //     -> Sa largeur est de 52 pixels
      Height = 35;                                           //     -> Sa hauteur est de 35 pixels
     if(f[i].RandomFish >= 60){                              //     -> Toutes les 1 seconde (60 frames),
         f[i].yRandom = random(-2.2,2.2);                    //        - Ce poisson change de vitesse au hasard sur l'axe y entre...
         if(f[i].yRandom >= 0 && f[i].yRandom < 1.5){        //
           f[i].yRandom = 1.5;                               //        ... 1.5 et 2.2...
         }                                                   //        ... et entre...
         if(f[i].yRandom < 0 && f[i].yRandom > -1.5){        //
           f[i].yRandom = -1.5;                              //        ... -2.2 et -1.5
         }                                                   //
         f[i].RandomFish = 0;                                //        - Reset au bout de 1 seconde le timer du changement de vitesse sur l'axe y    
      }                                                      //
      f[i].y =  f[i].y +  f[i].yRandom;                      //     -> Ce poisson bouge sur l'axe y avec la vitesse aléatoire sur l'axe y
      x = x - SpeedFishes;                                   //     -> Ce poisson avance sur l'axe x à une certaine vitesse selon le niveau choisi
      image(GoldFish[frameCount%60],x,y);                    //     -> Permet d'afficher le poisson avec son animation
    }                                                        //
}

  
  
  
  
void FishesMoveOnlyInTheRiver(){           // Fonction qui permet de gérer les collisions des poissons
    
     f[i].ColorReadTopRight = get((int)(f[i].x + f[i].Width) , (int)(f[i].y - 1));                 // On prend la valeur rgb du pixel en haut à droite de chaque poissons
     f[i].ColorReadTopLeft = get((int)(f[i].x - 1) , (int)(f[i].y - 1));                           // On prend la valeur rgb du pixel en haut à gauche de chaque poissons
     f[i].ColorReadBottomRight = get((int)(f[i].x + f[i].Width) , (int)(f[i].y + f[i].Height));    // On prend la valeur rgb du pixel en bas à droite de chaque poissons
     f[i].ColorReadBottomLeft = get((int)(f[i].x - 1) , (int)(f[i].y + f[i].Height));              // On prend la valeur rgb du pixel en bas à gauche de chaque poissons
     
    
   if(f[i].GreenFishGrabed == false || f[i].YellowFishGrabed == false || f[i].RedFishGrabed == false || f[i].GoldFishGrabed == false){ // --> Tant que le poisson n'est pas "attrapé"
     if(f[i].ColorReadTopRight == color(34,41,122) || f[i].ColorReadTopLeft == color(34,41,122)){                                      //     -> Si le la haut du poisson détecte la couleur bleu foncé
       f[i].y = f[i].y + 5;                                                                                                            //        - Le poisson descend de 5 pixels
       f[i].yRandom = -f[i].yRandom;                                                                                                   //        - Le poisson se déplace à l'opposé sur l'axe y
       f[i].RandomFish = 0;                                                                                                            //        - Le timer du changement de vitesse sur l'axe y est reset
     }                                                                                                                                 //
     if(f[i].ColorReadBottomRight == color(34,41,122) || f[i].ColorReadBottomLeft == color(34,41,122)){                                //     -> Si le bas du poisson détecte la couleur bleue foncée
       f[i].y = f[i].y - 5;                                                                                                            //        - Le poisson monte de 5 pixels
       f[i].yRandom = -f[i].yRandom;                                                                                                   //        - Le poisson se déplace à l'opposé sur l'axe y
       f[i].RandomFish = 0;                                                                                                            //        - Le timer du changement de vitesse sur l'axe y est reset
     }                                                                                                                                 //   
     if(f[i].ColorReadTopRight == color(64,64,64) || f[i].ColorReadTopLeft == color(64,64,64)             //     -> Si un des côtés du poisson détécte une couleur autre que
       || f[i].ColorReadBottomRight == color(64,64,64) || f[i].ColorReadBottomLeft == color(64,64,64)     //        le bleu foncé ou le bleu clair...
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
         f[i].x = -1000;                                                                                  //        - alors ce poisson est "mort", et donc part à gauche 
     }                                                                                                    //          de l'écran
   }                                                                                                      //
 }  
  
void Pecher(){             // Fonction qui permet de gérer la pêche des poissons et les points marqués
    
    ColorReadByHmc = get((int)xHmc+3,(int)yHmc);       // --> On prend la couleur du pixel juste à droite de l'hammecon
    
        
    //------------------------------------------------------------------------Pour pêcher les poissons verts------------------------------------------------------------------------//
  
   if(ColorReadByHmc == color(30,205,12) || ColorReadByHmc == color(1,1,1)                 // --> Tant que l'hammecon touche un poisson vert, 
  || ColorReadByHmc == color(254,254,254) || ColorReadByHmc == color(21,137,8)){           //     (Pour détecter les collider des poissons verts avec l'hammecon)
     GreenFishCollider = true;                                                             //     -> alors il y a "collider"     
  }                                                                                        //                                                                   
  else {                                                                                   // --> Sinon,                                                                    
   GreenFishCollider = false;                                                              //     -> alors il n'y a pas "collider"                                                            
  }                                                                                        //
                                                                    
  if(GreenFishCollider == true && f[i].Color == color(30,205,12)                                         // --> Tant qu'il y a "collider", que CE poisson est vert, 
     && xHmc >= f[i].x && xHmc <= (f[i].x+f[i].Width) && yHmc >= f[i].y && yHmc <= (f[i].y+f[i].Height)  //     que CE poisson est proche de l'hammecon, (pour detecter le bon poisson),
     && mousePressed == true && EcartHmc != 0) {                                                         //     que l'on click, et que l'hammecon est dans l'eau (pour qu'il y ait qu'un poisson peché à la fois),                                                   
    f[i].GreenFishGrabed = true;                                                                         //     -> alors CE poisson vert est "attrapé" !                                                                                                                                                                                                                                              
  }                                                                                                                                                                                                                                    
  
  if(f[i].GreenFishGrabed == true){                              // --> Tant que CE poisson est "attrapé", 
    GrabFishes();                                                //      -> La barre est activée
    f[i].x = xHmc - (f[i].Width/2);                              //      -> CE poisson est attaché à l'hammecon
    f[i].y = yHmc - (f[i].Height/2);                             //      
    if(yBarre >= 110  && GreenMouseReleased == true){            //      -> Si la barre est dans la zone verte et qu'on relache le click, 
                                                                 //          
      if(ComboGreenTimer >= 0  && ComboGreenTimer <= 60){        //          > Si le dernier poisson vert peché était entre 0 et 60s (5.6 à 6.6 s),
        score++;                                                 //            - le score augmente de 1 ! (score = +1 x1)
      }                                                          //
      if(ComboGreenTimer > 60 && ComboGreenTimer <= 120){        //          > Si le dernier poisson vert peché était entre 60+ et 120 (4.6 à 5.6 s), 
        score = score + 2;                                       //            - le score augmente de 2 ! (score = +1 x2)
        ComboScoreX2 = true;                                     //            - Active l'image "combo x2" 
        coombooo.trigger();                                      //            - le son "combooo" est activé
      }                                                          //
      if(ComboGreenTimer > 120 && ComboGreenTimer <= 180){       //          > Si le dernier poisson vert peché était entre 120+ et 180 (3.6 à 4.6 s), 
        score = score + 3;                                       //            - le score augmente de 3 ! (score = +1 x3)
       ComboScoreX3 = true;                                      //            - Active l'image "combo x3"
       coombooo.trigger();                                       //            - le son "combooo" est activé
      }                                                          //
      if(ComboGreenTimer > 180 && ComboGreenTimer <= 240){       //          > Si le dernier poisson vert peché était entre 180+ et 240 (2.6 à 3.6 s),
        score = score + 4;                                       //            - le score augmente de 4 ! (score = +1 x4)
        ComboScoreX4 = true;                                     //            - Active l'image "combo x4"
        coombooo.trigger();                                      //            - le son "combooo" est activé
      }                                                          //
      if(ComboGreenTimer > 240 && ComboGreenTimer <= 400){       //          > Si le dernier poisson vert peché était entre 240+ et 400 (0 à 2.6 s),
        score = score + 5;                                       //            - le score augmente de 5 ! (score = +1 x5)
        ComboScoreX5 = true;                                     //            - Active l'image "combo x5"
        coombooo.trigger();                                      //            - le son "combooo" est activé
      }                                                          //
      Score1 = true;                                             //          > Active l'image "score +1"
      ComboScoreTimer = 30;                                      //          > Démarre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence à 30 jusuqu'à 0 = 30 frames = 1/2 s)
      ComboGreenTimer = 400;                                     //          > Démarre le timer du combo pour les poisson verts (ComboGreenTimer commence à 400 jusqu'à 45 = 355 frames = 5.9166.. s)   
      succes.trigger();                                          //          > le son du succes est activé
      fishingroad.trigger();                                     //          > le son du poisson qui sort de l'eau est activé
      f[i].GreenFishGrabed = false;                              //          > CE poisson n'est plus "attaché"                                                                 
      f[i].x = -1000;                                            //          > On envoi CE poisson hors de la camera                                                                      
      GreenMouseReleased = false;                                //          > Permet que la barre de précision se désactive
      FilTimer = 180;                                            //          > Permet de mettre l'hammecon dans l'eau, et donc de repécher                                                                       
    }                                                            //                                                                             
    if(yBarre < 110 && GreenMouseReleased == true){              //      -> Si la barre n'est pas dans la zone verte et qu'on relache le click,                                                                     
      score--;                                                   //          > Le score diminue de 1 !
      Scoremoins1 = true;                                        //          > Active l'image "score -1"
      fail.trigger();                                            //          > le son du fail est activé
      ComboScoreTimer = 30;                                      //          > Démarre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence à 30 jusuqu'à 0 = 30 frames = 1/2 s)
      ComboGreenTimer = 45;                                      //          > Met le timer du combo pour les poisson verts à l'initial ( ComboGreenTimer se remet à 45)             
      f[i].GreenFishGrabed = false;                              //          > CE poisson n'est plus "attaché"                                                                                                                                                                                              
      f[i].x = xHmc - 100;                                       //          > On envoi CE poisson un peu derrière l'hammecon                                                                     
      GreenMouseReleased = false;                                //          > Permet que la barre de précision se désactive
      FilTimer = 180;                                            //          > Permet de mettre l'hammecon dans l'eau, et donc de repécher                                                    
    }                                                            //                                                                                 
}                                                                //                                                                               



    //------------------------------------------------------------------------Pour pêcher les poissons jaunes------------------------------------------------------------------------//
  
  if(ColorReadByHmc == color(255,225,8) || ColorReadByHmc == color(2,2,2)                  // --> Tant que l'hammecon touche un poisson jaune, 
  || ColorReadByHmc == color(253,253,253) || ColorReadByHmc == color(255,178,8)){          //     (Pour détecter les collider des poissons jaunes avec l'hammecon)
     YellowFishCollider = true;                                                            //     -> alors il y a "collider"     
  }                                                                                        //                                                                   
  else {                                                                                   // --> Sinon,                                                                    
   YellowFishCollider = false;                                                             //     -> alors il n'y a pas "collider"                                                            
  }                                                                                        //
                                                                    
  if(YellowFishCollider == true && f[i].Color == color(255,225,8)                                        // --> Tant qu'il y a "collider", que CE poisson est jaune, 
     && xHmc >= f[i].x && xHmc <= (f[i].x+f[i].Width) && yHmc >= f[i].y && yHmc <= (f[i].y+f[i].Height)  //     que CE poisson est proche de l'hammecon, (pour detecter le bon poisson),
     && mousePressed == true && EcartHmc != 0) {                                                         //     que l'on click, et que l'hammecon est dans l'eau (pour qu'il y ait qu'un poisson peché à la fois),                                                   
    f[i].YellowFishGrabed = true;                                                                        //     -> alors CE poisson jaune est "attrapé" !                                                                                                                                                                                                                                             
  }                                                                                                                                                                                                                                    
  
  if(f[i].YellowFishGrabed == true){                              // --> Tant que CE poisson est "attrapé", 
    GrabFishes();                                                 //      -> La barre est activée
      f[i].x = xHmc - (f[i].Width/2);                             //      -> CE poisson est attaché à l'hammecon
    f[i].y = yHmc - (f[i].Height/2);                              //      
    if(yBarre >= 50 && yBarre < 110                               //      -> Si la barre est dans la zone jaune et qu'on relache le click,
    && YellowMouseReleased == true){                              //
                                                                  //          
      if(ComboYellowTimer >= 0  && ComboYellowTimer <= 60){       //          > Si le dernier poisson jaune peché était entre 0 et 60s (5.6 à 6.6 s),
        score = score + 2;                                        //            - le score augmente de 2 ! (score = +2 x1)
      }                                                           //
      if(ComboYellowTimer > 60 && ComboYellowTimer <= 120){       //          > Si le dernier poisson jaune peché était entre 60+ et 120 (4.6 à 5.6 s), 
        score = score + 4;                                        //            - le score augmente de 4 ! (score = +2 x4)
        ComboScoreX2 = true;                                      //            - Active l'image "combo x2" 
        coombooo.trigger();                                       //            - le son "combooo" est activé
      }                                                           //
      if(ComboYellowTimer > 120 && ComboYellowTimer <= 180){      //          > Si le dernier poisson jaune peché était entre 120+ et 180 (3.6 à 4.6 s), 
        score = score + 6;                                        //            - le score augmente de 6 ! (score = +2 x3)
       ComboScoreX3 = true;                                       //            - Active l'image "combo x3"
       coombooo.trigger();                                        //            - le son "combooo" est activé
      }                                                           //
      if(ComboYellowTimer > 180 && ComboYellowTimer <= 240){      //          > Si le dernier poisson jaune peché était entre 180+ et 240 (2.6 à 3.6 s),
        score = score + 8;                                        //            - le score augmente de 8 ! (score = +2 x4)
        ComboScoreX4 = true;                                      //            - Active l'image "combo x4"
        coombooo.trigger();                                       //            - le son "combooo" est activé
      }                                                           //
      if(ComboYellowTimer > 240 && ComboYellowTimer <= 400){      //          > Si le dernier poisson jaune peché était entre 240+ et 400 (0 à 2.6 s),
        score = score + 10;                                       //            - le score augmente de 10 ! (score = +2 x5)
        ComboScoreX5 = true;                                      //            - Active l'image "combo x5"
        coombooo.trigger();                                       //            - le son "combooo" est activé
      }                                                           //
      Score2 = true;                                              //          > Active l'image "score +2"
      ComboScoreTimer = 30;                                       //          > Démarre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence à 30 jusuqu'à 0 = 30 frames = 1/2 s)
      ComboYellowTimer = 400;                                     //          > Démarre le timer du combo pour les poisson jaune (ComboYellowTimer commence à 400 jusqu'à 45 = 355 frames = 5.9166.. s)   
      succes.trigger();                                           //          > le son du succes est activé
      fishingroad.trigger();                                      //          > le son du poisson qui sort de l'eau est activé
      f[i].YellowFishGrabed = false;                              //          > CE poisson n'est plus "attaché"                                                                 
      f[i].x = -1000;                                             //          > On envoi CE poisson hors de la camera                                                                      
      YellowMouseReleased = false;                                //          > Permet que la barre de précision se désactive
      FilTimer = 180;                                             //          > Permet de mettre l'hammecon dans l'eau, et donc de repécher                                                                       
    }                                                             //                                                                             
    if((yBarre >= 110 && YellowMouseReleased == true)             //      -> Si la barre n'est pas dans la zone jaune et qu'on relache le click, 
    || (yBarre < 50 && YellowMouseReleased == true)){              //                                                                    
      score--;                                                    //          > Le score diminue de 1 !
      Scoremoins1 = true;                                         //          > Active l'image "score -1"
      fail.trigger();                                             //          > le son du fail est activé
      ComboScoreTimer = 30;                                       //          > Démarre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence à 30 jusuqu'à 0 = 30 frames = 1/2 s)
      ComboYellowTimer = 45;                                      //          > Met le timer du combo pour les poisson jaune à l'initial ( ComboYellowTimer se remet à 45)             
      f[i].YellowFishGrabed = false;                              //          > CE poisson n'est plus "attaché"                                                                                                                                                                                                
      f[i].x = xHmc - 100;                                        //          > On envoi CE poisson un peu derrière l'hammecon                                                                     
      YellowMouseReleased = false;                                //          > Permet que la barre de précision se désactive
      FilTimer = 180;                                             //          > Permet de mettre l'hammecon dans l'eau, et donc de repécher                                                    
    }                                                             //                                                                                 
}                                                                 //                                                                               



    //------------------------------------------------------------------------Pour pêcher les poissons rouges------------------------------------------------------------------------//
  
   if(ColorReadByHmc == color(222,18,18) || ColorReadByHmc == color(3,3,3)                 // --> Tant que l'hammecon touche un poisson rouge, 
  || ColorReadByHmc == color(252,252,252) || ColorReadByHmc == color(175,15,15)){          //     (Pour détecter les collider des poissons rouges avec l'hammecon)
     RedFishCollider = true;                                                               //     -> alors il y a "collider"     
  }                                                                                        //                                                                   
  else {                                                                                   // --> Sinon,                                                                    
   RedFishCollider = false;                                                                //     -> alors il n'y a pas "collider"                                                            
  }                                                                                        //
                                                                    
  if(RedFishCollider == true && f[i].Color == color(222,18,18)                                           // --> Tant qu'il y a "collider", que CE poisson est rouge, 
     && xHmc >= f[i].x && xHmc <= (f[i].x+f[i].Width) && yHmc >= f[i].y && yHmc <= (f[i].y+f[i].Height)  //     que CE poisson est proche de l'hammecon, (pour detecter le bon poisson),
     && mousePressed == true && EcartHmc != 0) {                                                         //     que l'on click, et que l'hammecon est dans l'eau (pour qu'il y ait qu'un poisson peché à la fois),                                                   
    f[i].RedFishGrabed = true;                                                                           //     -> alors CE poisson rouge est "attrapé" !                                                                                                                                                                                                                                             
  }                                                                                                                                                                                                                                    
  
  if(f[i].RedFishGrabed == true){                                // --> Tant que CE poisson est "attrapé", 
    GrabFishes();                                                //      -> La barre est activée
    f[i].x = xHmc - (f[i].Width/2);                              //      -> CE poisson est attaché à l'hammecon
    f[i].y = yHmc - (f[i].Height/2);                             //      
    if(yBarre >= 20 && yBarre < 50 && RedMouseReleased == true){ //      -> Si la barre est dans la zone rouge et qu'on relache le click, 
                                                                 //          
      if(ComboRedTimer >= 0  && ComboRedTimer <= 60){            //          > Si le dernier poisson rouge peché était entre 0 et 60s (5.6 à 6.6 s),
        score = score + 5;                                       //            - le score augmente de 5 ! (score = +5 x1)
      }                                                          //
      if(ComboRedTimer > 60 && ComboRedTimer <= 120){            //          > Si le dernier poisson rouge peché était entre 60+ et 120 (4.6 à 5.6 s), 
        score = score + 10;                                      //            - le score augmente de 10 ! (score = +5 x2)
        ComboScoreX2 = true;                                     //            - Active l'image "combo x2" 
        coombooo.trigger();                                      //            - le son "combooo" est activé
      }                                                          //
      if(ComboRedTimer > 120 && ComboRedTimer <= 180){           //          > Si le dernier poisson rouge peché était entre 120+ et 180 (3.6 à 4.6 s), 
        score = score + 15;                                      //            - le score augmente de  15! (score = +5 x3)
       ComboScoreX3 = true;                                      //            - Active l'image "combo x3"
       coombooo.trigger();                                       //            - le son "combooo" est activé
      }                                                          //
      if(ComboRedTimer > 180 && ComboRedTimer <= 240){           //          > Si le dernier poisson rouge peché était entre 180+ et 240 (2.6 à 3.6 s),
        score = score + 20;                                      //            - le score augmente de 20 ! (score = +5 x4)
        ComboScoreX4 = true;                                     //            - Active l'image "combo x4"
        coombooo.trigger();                                      //            - le son "combooo" est activé
      }                                                          //
      if(ComboRedTimer > 240 && ComboRedTimer <= 400){           //          > Si le dernier poisson rouge peché était entre 240+ et 400 (0 à 2.6 s),
        score = score + 25;                                      //            - le score augmente de 25 ! (score = +5 x5)
        ComboScoreX5 = true;                                     //            - Active l'image "combo x5"
        coombooo.trigger();                                      //            - le son "combooo" est activé
      }                                                          //
      Score4 = true;                                             //          > Active l'image "score +5"
      ComboScoreTimer = 30;                                      //          > Démarre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence à 30 jusuqu'à 0 = 30 frames = 1/2 s)
      ComboRedTimer = 400;                                       //          > Démarre le timer du combo pour les poisson rouges (ComboRedTimer commence à 400 jusqu'à 45 = 355 frames = 5.9166.. s)   
      succes.trigger();                                          //          > le son du succes est activé
      fishingroad.trigger();                                     //          > le son du poisson qui sort de l'eau est activé
      f[i].RedFishGrabed = false;                                //          > CE poisson n'est plus "attaché"                                                                 
      f[i].x = -1000;                                            //          > On envoi CE poisson hors de la camera                                                                      
      RedMouseReleased = false;                                  //          > Permet que la barre de précision se désactive
      FilTimer = 180;                                            //          > Permet de mettre l'hammecon dans l'eau, et donc de repécher                                                                       
    }                                                            //                                                                             
    if((yBarre >= 50 && RedMouseReleased == true)                //      -> Si la barre n'est pas dans la zone rouge et qu'on relache le click, 
    ||(yBarre < 20 && RedMouseReleased == true)){                //                                                                    
      score--;                                                   //          > Le score diminue de 1 !
      Scoremoins1 = true;                                        //          > Active l'image "score -1"
      fail.trigger();                                            //          > le son du fail est activé
      ComboScoreTimer = 30;                                      //          > Démarre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence à 30 jusuqu'à 0 = 30 frames = 1/2 s)
      ComboRedTimer = 45;                                        //          > Met le timer du combo pour les poisson rouges à l'initial ( ComboRedTimer se remet à 45)             
      f[i].RedFishGrabed = false;                                //          > CE poisson n'est plus "attaché"                                                                                                                                                                                                 
      f[i].x = xHmc - 100;                                       //          > On envoi CE poisson un peu derrière l'hammecon                                                                     
      RedMouseReleased = false;                                  //          > Permet que la barre de précision se désactive
      FilTimer = 180;                                            //          > Permet de mettre l'hammecon dans l'eau, et donc de repécher                                                    
    }                                                            //                                                                                 
}                                                                //                                                                               

    //------------------------------------------------------------------------Pour pêcher les poissons dorés------------------------------------------------------------------------//
  
   if(ColorReadByHmc == color(212,175,55) || ColorReadByHmc == color(4,4,4)                // --> Tant que l'hammecon touche un poisson doré, 
  || ColorReadByHmc == color(251,251,251) || ColorReadByHmc == color(188,153,49)){         //     (Pour détecter les collider des poissons doré avec l'hammecon)
     GoldFishCollider = true;                                                              //     -> alors il y a "collider"     
  }                                                                                        //                                                                   
  else {                                                                                   // --> Sinon,                                                                    
   GoldFishCollider = false;                                                               //     -> alors il n'y a pas "collider"                                                            
  }                                                                                        //
                                                                    
  if(GoldFishCollider == true && f[i].Color == color(212,175,55)                                         // --> Tant qu'il y a "collider", que CE poisson est doré, 
     && xHmc >= f[i].x && xHmc <= (f[i].x+f[i].Width) && yHmc >= f[i].y && yHmc <= (f[i].y+f[i].Height)  //     que CE poisson est proche de l'hammecon, (pour detecter le bon poisson),
     && mousePressed == true && EcartHmc != 0) {                                                         //     que l'on click, et que l'hammecon est dans l'eau (pour qu'il y ait qu'un poisson peché à la fois),                                                   
    f[i].GoldFishGrabed = true;                                                                          //     -> alors CE poisson doré est "attrapé" !                                                                                                                                                                                                                                              
  }                                                                                                                                                                                                                                    
  
  if(f[i].GoldFishGrabed == true){                               // --> Tant que CE poisson est "attrapé", 
    GrabFishes();                                                //      -> La barre est activée
    f[i].x = xHmc - (f[i].Width/2);                              //      -> CE poisson est attaché à l'hammecon
    f[i].y = yHmc - (f[i].Height/2);                             //      
    if(yBarre >= 5 && yBarre < 20 && GoldMouseReleased == true){ //      -> Si la barre est dans la zone doré et qu'on relache le click, 
                                                                 //          
      if(ComboGoldTimer >= 0  && ComboGoldTimer <= 60){          //          > Si le dernier poisson doré peché était entre 0 et 60s (5.6 à 6.6 s),
        score = score + 15;                                      //            - le score augmente de 15 ! (score = +15 x1)
      }                                                          //
      if(ComboGoldTimer > 60 && ComboGoldTimer <= 120){          //          > Si le dernier poisson doré peché était entre 60+ et 120 (4.6 à 5.6 s), 
        score = score + 30;                                      //            - le score augmente de 30 ! (score = +15 x2)
        ComboScoreX2 = true;                                     //            - Active l'image "combo x2" 
        coombooo.trigger();                                      //            - le son "combooo" est activé
      }                                                          //
      if(ComboGoldTimer > 120 && ComboGoldTimer <= 180){         //          > Si le dernier poisson doré peché était entre 120+ et 180 (3.6 à 4.6 s), 
        score = score + 45;                                      //            - le score augmente de  45! (score = +15 x3)
       ComboScoreX3 = true;                                      //            - Active l'image "combo x3"
       coombooo.trigger();                                       //            - le son "combooo" est activé
      }                                                          //
      if(ComboGoldTimer > 180 && ComboGoldTimer <= 240){         //          > Si le dernier poisson doré peché était entre 180+ et 240 (2.6 à 3.6 s),
        score = score + 60;                                      //            - le score augmente de 60 ! (score = +15 x4)
        ComboScoreX4 = true;                                     //            - Active l'image "combo x4"
        coombooo.trigger();                                      //            - le son "combooo" est activé
      }                                                          //
      if(ComboGoldTimer > 240 && ComboGoldTimer <= 400){         //          > Si le dernier poisson doré peché était entre 240+ et 400 (0 à 2.6 s),
        score = score + 75;                                      //            - le score augmente de 75 ! (score = +15 x5)
        ComboScoreX5 = true;                                     //            - Active l'image "combo x5"
        coombooo.trigger();                                      //            - le son "combooo" est activé
      }                                                          //
      Score8 = true;                                             //          > Active l'image "score +15"
      ComboScoreTimer = 30;                                      //          > Démarre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence à 30 jusuqu'à 0 = 30 frames = 1/2 s)
      ComboGoldTimer = 400;                                      //          > Démarre le timer du combo pour les poisson doré (ComboGoldTimer commence à 400 jusqu'à 45 = 355 frames = 5.9166.. s)   
      succes.trigger();                                          //          > le son du succes est activé
      fishingroad.trigger();                                     //          > le son du poisson qui sort de l'eau est activé
      f[i].GoldFishGrabed = false;                               //          > CE poisson n'est plus "attaché"                                                                 
      f[i].x = -1000;                                            //          > On envoi CE poisson hors de la camera                                                                      
      GoldMouseReleased = false;                                 //          > Permet que la barre de précision se désactive
      FilTimer = 180;                                            //          > Permet de mettre l'hammecon dans l'eau, et donc de repécher                                                                       
    }                                                            //                                                                             
    if((yBarre >= 20 && GoldMouseReleased == true)               //      -> Si la barre n'est pas dans la zone doré et qu'on relache le click, 
    ||(yBarre < 5 && GoldMouseReleased == true)){                //                                                                    
      score--;                                                   //          > Le score diminue de 1 !
      Scoremoins1 = true;                                        //          > Active l'image "score -1"
      fail.trigger();                                            //          > le son du fail est activé
      ComboScoreTimer = 30;                                      //          > Démarre le timer pour afficher les images score et combo pendant 1/2 s (ComboScoreTimer commence à 30 jusuqu'à 0 = 30 frames = 1/2 s)
      ComboGoldTimer = 45;                                       //          > Met le timer du combo pour les poisson doré à l'initial ( ComboGoldTimer se remet à 45)             
      f[i].GoldFishGrabed = false;                               //          > CE poisson n'est plus "attaché"                                                                                                                                                                                              
      f[i].x = xHmc - 100;                                       //          > On envoi CE poisson un peu derrière l'hammecon                                                                     
      GoldMouseReleased = false;                                 //          > Permet que la barre de précision se désactive
      FilTimer = 180;                                            //          > Permet de mettre l'hammecon dans l'eau, et donc de repécher                                                    
    }                                                            //                                                                                 
  }                                                              //                                                                               
 }

}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

void mouseReleased(){                // Fonction pour détecter quand le click de la souris est relachée
  
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
  
  
void InvokeFishes() {             // Fonction pour le programme de tous les poissons (object Fishes)
  
    for(i=0;i<=99;i++){
       f[i].DisplayFish();                // Affiche les poissons 
       f[i].FishesMoveOnlyInTheRiver();   // poissons restent sur la rivière (collisions des poissons)
       f[i].Pecher();                     // gestion de la pêche des poissons et des points marqués
       
        
    }
      
   
  
}

