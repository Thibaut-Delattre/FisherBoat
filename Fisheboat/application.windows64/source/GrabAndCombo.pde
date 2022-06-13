float xBarre;  // Coordonnée x de la barre
float yBarre;  // Coordonnée y de la barre

void GrabFishes() {    // Fonction pour permettre d'afficher la barre de jauge 
  
 fill(61,61,53,150);         // Rectangle semi transparent
 rect(80,0,235,250);         // en fond
  
  strokeJoin(ROUND);         // Contour arrondi
  stroke(#636363);           // Couleur des contours
  strokeWeight(2);           // Epaisseur des contours
  
  fill(30,205,12);           // Couleur verte 
  rect(100,110,200,120);     // Zone verte
  
  fill(255,225,8);           // Couleur jaune      // cible
  rect(100,50,200,60);       // Zone jaune
     
  fill(222,18,18);           // Couleur Rouge       // cible
  rect(100,20,200,30);       // Zone rouge
  
  fill(212,175,55);          // Couleur doré
  rect(100,5,200,15);        // Zone doré
  
  
  
  xBarre = 200;          // Position de
  yBarre = yBarre - 3;   // la barre
  if(yBarre < 5){        //
    yBarre = 230;        //
  }
  
  strokeWeight(1);                  // Eppaisseur du contour de la barre
  fill(0,0,0);                      // Couleur noir de la barre
  ellipse(xBarre,yBarre,250,2);     // barre
  
}



void ComboScore(){       // Fonction pour permettre d'afficher les barre de jauge "Combo"
  
  
   if(ComboGreenTimer >= 45){                       // Permet de faire descendre la barre de
      ComboGreenTimer--;                            // jauge du combo des poissons verts
   }                                                //
   if(ComboYellowTimer >= 45){                      // Permet de faire descendre la barre de
      ComboYellowTimer = ComboYellowTimer - 0.75;   // jauge du combo des poissons jaunes
   }                                                //
   if(ComboRedTimer >= 45){                         // Permet de faire descendre la barre de
      ComboRedTimer = ComboRedTimer - 0.50;         // jauge du combo des poissons rouges
   }                                                //
   if(ComboGoldTimer >= 45){                        // Permet de faire descendre la barre de
      ComboGoldTimer = ComboGoldTimer - 0.25;       // jauge du combo des poissons dorés
   }                                                //
    
if(ComboScoreTimer >= 1){             // Timer pour afficher les images score et combo pendant 1/2 s
      ComboScoreTimer--;              // (ComboScoreTimer commence à 30 jusuqu'à 0 = 30 frames = 1/2 s)
}                                     //
   
    if(ComboScoreTimer == 0){             // A la fin du timer pour afficher les scores et combo, 
      ComboScoreX2 = false;               //  > l'image "X2" n'est plus affichée
      ComboScoreX3 = false;               //  > l'image "X3" n'est plus affichée
      ComboScoreX4 = false;               //  > l'image "X4" n'est plus affichée
      ComboScoreX5 = false;               //  > l'image "X5" n'est plus affichée
      Score1 = false;                     //  > l'image "+1" n'est plus affichée
      Score2 = false;                     //  > l'image "+2" n'est plus affichée
      Score4 = false;                     //  > l'image "+5" n'est plus affichée
      Score8 = false;                     //  > l'image "+15" n'est plus affichée
      Scoremoins1 = false;                //  > l'image "-1" n'est plus affichée
      ComboImg = loadImage("Combo0.png"); //  > l'image des combos est transparente
      ScoreImg = loadImage("Score0.png"); //  > l'image des scores est transparente
    }                                     //
    
    fill(61,61,53,150);                       //
     if(ComboScoreX2 == true){                // Si l'image "X2" est activé,
      rect(580,80,300,120);                   // -> Le fond semi transparent apparaît
      ComboImg = loadImage("Combox2.png");    // -> l'image "X2" est affichée
    }                                         //
     if(ComboScoreX3 == true){                // Si l'image "X3" est activé,
      rect(580,80,300,120);                   // -> Le fond semi transparent apparaît
      ComboImg = loadImage("Combox3.png");    // -> l'image "X3" est affichée
    }                                         //
     if(ComboScoreX4 == true){                // Si l'image "X4" est activé,
      rect(580,80,300,120);                   // -> Le fond semi transparent apparaît
      ComboImg = loadImage("Combox4.png");    // -> l'image "X4" est affichée
    }                                         //
     if(ComboScoreX5 == true){                // Si l'image "X5" est activé,
      rect(580,80,300,120);                   // -> Le fond semi transparent apparaît
      ComboImg = loadImage("Combox5.png");    // -> l'image "X5" est affichée
    }                                         //
    if(Score1 == true){                       // Si l'image "+1" est activé,
      rect(580,80,300,120);                   // -> Le fond semi transparent apparaît
      ComboImg = loadImage("score+1.png");    // -> l'image "+1" est affichée
    }                                         //
     if(Score2 == true){                      // Si l'image "+2" est activé,
      rect(580,80,300,120);                   // -> Le fond semi transparent apparaît
      ComboImg = loadImage("score+2.png");    // -> l'image "+2" est affichée
    }                                         //
     if(Score4 == true){                      // Si l'image "+5" est activé,
      rect(580,80,300,120);                   // -> Le fond semi transparent apparaît
      ComboImg = loadImage("score+4.png");    // -> l'image "+5" est affichée
    }                                         //
     if(Score8 == true){                      // Si l'image "+15" est activé,
      rect(580,80,300,120);                   // -> Le fond semi transparent apparaît
      ComboImg = loadImage("score+8.png");    // -> l'image "+15" est affichée
    }                                         //
     if(Scoremoins1 == true){                 // Si l'image "-1" est activé,
      rect(580,80,300,120);                   // -> Le fond semi transparent apparaît
      ComboImg = loadImage("score-1.png");    // -> l'image "-1" est affichée
    }                                         //
    

    image(ScoreImg,575, 40);        // Afficher les images des scores
    image(ComboImg,700,40);         // Afficher les images des combos
    
    
    
    
    
//--------------------------------------------------Affiche les barre de jauge des combos--------------------------------------------------//

    strokeWeight(0);       // Epaisseur des contours
    fill(227,232,190);
    rect(1175,5,200,215);
    
    strokeJoin(ROUND);     // Contour arrondi
    stroke(#636363);       // Couleur des contours
    
    
    fill(30,205,12);
    rect(1200,10,30,(ComboGreenTimer/2)+1);
    fill(255,225,8);
    rect(1240,10,30,(ComboYellowTimer/2)+1);
    fill(222,18,18);
    rect(1280,10,30,(ComboRedTimer/2)+1);
    fill(212,175,55);
    rect(1320,10,30,(ComboGoldTimer/2)+1);
    
    stroke(#636363);      // Couleur des contours
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

