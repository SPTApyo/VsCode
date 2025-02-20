#include <stdio.h>
#include <math.h>

#define TRUE 1
#define FALSE 0

/* Définitions des structures */

typedef struct {
  double x;
  double y;
} point_t;

typedef struct {
  point_t centre;
  double rayon;
} cercle_t;

typedef struct {
  point_t *a, *b, *c;
} triangle_t;

/* Définitions de vos fonctions du TP */
/**
 * Affiche un point sous la forme (x;y).
 * Les nombres sont affichés avec une seule décimale. Ex. (4.0;0.5)
 */
void afficher_point(point_t p){
  printf("(%0.1f;%0.1f)", p.x, p.y);
}

/**
 * Retourne la distance entre deux points.
 */
double distance(point_t *a, point_t *b){
  return sqrt(pow((a->x - b->x), 2) + pow((a->y - b->y), 2));
}

int presque_egal(double a, double b){
  double epsilon = 0.000000001;
  double delta = a - b;
  if (-epsilon <= delta && delta <= epsilon) {
    return TRUE;
  } else {
    return FALSE;
  }
}



/**
 * Determine si un triangle est rectangle et en quel sommet
 * 
 * @return un pointeur vers un sommet a angle droit
 * @return NULL si le triangle n'est pas rectangle
 */
point_t *angle_droit(triangle_t *t){
  double ab = distance(t->a, t->b);
  double bc = distance(t->b, t->c);
  double ac = distance(t->a, t->c);
  if (ab > bc && ab > ac){
    if (presque_egal(pow(ab, 2),(pow(bc, 2) + pow(ac, 2))) == TRUE){
      return t->a;
    }
  } else if (bc > ab && bc > ac){
    if (presque_egal(pow(bc, 2),(pow(ab, 2) + pow(ac, 2))) == TRUE){
      return t->b;
    }
  } else {
    if (presque_egal(pow(ac, 2),(pow(ab, 2) + pow(bc, 2))) == TRUE){
      return t->c;
    }
  }
  return NULL;
}


void cercle_circonscrit(triangle_t *t, cercle_t *c){
  double ab = distance(t->a, t->b);
  double bc = distance(t->b, t->c);
  double ac = distance(t->a, t->c);
  double p = (ab + bc + ac) / 2;
  double r = (ab * bc * ac) / (4 * sqrt(p * (p - ab) * (p - bc) * (p - ac)));
  c->rayon = r;
  c->centre.x = (t->a->x + t->b->x + t->c->x) / 3;
  c->centre.y = (t->a->y + t->b->y + t->c->y) / 3;
}


/* Fonction main() pour appeler vos fonctions */

int main(void) {
  printf("Bonjour TP2 !\n");

  ///* exercice 1
  point_t A = {1.0, 1.0};
  point_t B = {2.0, 3.0};
  printf("\nA=");
  afficher_point(A);
  printf("\nB=");
  afficher_point(B);
  //*/


  printf("\nDistance entre A et B: %0.2f\n", distance(&A, &B));
  A.y +=2.5;
  B.y +=2.5;
  printf("\nDistance entre A et B: %0.2f\n", distance(&A, &B));

  /* exercice 3
  cercle_t C = {{0, 0}, 1.0};
  point_t A = {0, 0};
  printf("Saisir le centre (x 'espace' y) : ");
  scanf("%lf %lf", &C.centre.x, &C.centre.y);
  printf("Saisir le rayon : ");
  scanf("%lf", &C.rayon);
  while (TRUE)
  {
    printf("Donner un point (x puis y) : ");
    scanf("%lf %lf", &A.x, &A.y);
    if (distance(&C.centre, &A) < C.rayon){
      printf("Le point ");
      afficher_point(A);
      printf(" est dans le cercle\n");
    } else {
      printf("Le point ");
      afficher_point(A);
      printf(" est hors du cercle\n");
    }
  }
  */

   /* exercice 4
  point_t
    P = {0, 3},
    Q = {4, 0},
    R = {4, 3};
  triangle_t T = {&P, &Q, &R};
  point_t *angle = angle_droit(&T);
  if (angle != NULL){
    printf("Le triangle est rectangle en ");
    afficher_point(*angle);
    printf("\n");
  } else {
    printf("Le triangle n'est pas rectangle\n");
  }
  */

  /* exercice  5
  point_t
  P = {0, 3},
  Q = {4, 0},
  R = {4, 3};
  triangle_t T = {&P, &Q, &R};
  cercle_t C = {{0, 0}, 0};
  cercle_circonscrit(&T, &C);
  printf("Le cercle circonscrit au triangle a pour centre ");
  afficher_point(C.centre);
  printf(" et pour rayon %0.2f\n", C.rayon);
  */
  return 0;
}