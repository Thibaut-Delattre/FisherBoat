color ColorLeftBoat;           // Couleur qui prendra la valeur rgb du pixel en bas à gauche du bateau
color ColorMidBoat;            // Couleur qui prendra la valeur rgb du pixel en bas au milieu du bateau
color ColorRightBoat;          // Couleur qui prendra la valeur rgb du pixel en bas à droite du bateau
color ColorReadByTopBoat;      // Couleur qui prendra la valeur rgb du pixel en haut à gauche du bateau
color ColorReadByBottomBoat;   // Couleur qui prendra la valeur rgb du pixel en bas à gauche du bateau

int score;    // Score


void keyPressed(){                            //
  if(key==CODED){                             //
    if(keyCode==DOWN) yBoat = yBoat + 5 ;     // Fait déplacer le bateau vers le bas à une vitesse de 5
    if(keyCode==UP) yBoat = yBoat - 5 ;       // Fait déplacer le bateau vers le haut à une vitesse de 5
  }                                           //
}                                             //

void textsAndBackground() {     // Fonction pour afficher le score, la distance parcouru, le background...
  
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


void BoatMoveOnlyInTheRiver(){        // Fonction qui permet de gérer les collisions du bateau
 
  ColorReadByTopBoat = get((int)xBoat - 1, (int)yBoat + 80);          // On prend la valeur rgb du pixel en haut à gauche du bateau
  ColorReadByBottomBoat = get((int)xBoat - 1, (int)yBoat + 130);      // On prend la valeur rgb du pixel en bas à gauche du bateau
  ColorLeftBoat = get((int)xBoat , (int)yBoat + 110);                 // On prend la valeur rgb du pixel en bas à gauche du bateau
  ColorMidBoat = get((int)xBoat + 102 , (int)yBoat + 110);            // On prend la valeur rgb du pixel en bas au milieu du bateau
  ColorRightBoat = get((int)xBoat + 155, (int)yBoat + 110);           // On prend la valeur rgb du pixel en bas à droite du bateau
  
  
  if(ColorReadByTopBoat == color(87,220,31)){         // Si le haut du bateau détecte le vert de l'herbe
   yBoat = yBoat + 5;                                 // -> Le bateau se déplace vers le bas                    
  }                                                   //
  if(ColorReadByBottomBoat == color(87,220,31)){      // Si le bas du bateau détecte le vert de l'herbe                
   yBoat = yBoat - 5;                                 // -> Le bateau se déplace vers le haut    
  }                                                   //
  
  if(ColorLeftBoat == color(64,64,64) || ColorMidBoat == color(64,64,64)     // Si le bas du bateau détecte la couleur du rocher 
     || ColorRightBoat == color(64,64,64)){                                  //
    scene = 666;                                                             // -> Vous êtes mort, et la scène qui indique que
  }                                                                          //    vous êtes mort apparaît
}

void Hmc_Boat_Fil(){       // Fonctiion pour apparaître le bateau, le fil et l'hammecon
  
   if(mousePressed == true){                        // Quand on click sur la souris,  
     EcartHmc = 0;                                  // -> La canne à pêche est levée
     FilTimer = 0;                                  // 
  }          
                                                        
  if(EcartHmc == 0){                // Démare le timer (barre rose en haut de l'écran)
    FilTimer++;                     // pour pouvoir repêcher
  }
  if(EcartHmc == 50){               // Si on peut pêcher
    FilTimer = 180;                 // -> la barre rose est rempli entièrement
  }  
                                                                     
  if(FilTimer > 180 && mousePressed == false){          // Si la barre rose est rempli entièrement  
    yBarre = 230;                                       // -> La barre noir est reset
    EcartHmc = 50;                                      // -> la canne à pêche peut pêcher
    FilTimer = 180;                                     // -> Pour ne pas dépasser la barre rose           
  }                                                     //
  
  fill(221,0,192);        
  strokeWeight(0);
  rect(500, 10, FilTimer*2 + 10, 50);     //Affichage des la barre rose en haut de l'écran
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


