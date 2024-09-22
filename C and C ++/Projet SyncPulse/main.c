/*
*   Version 0.1
*   Author : SPT_APYO
*
*/


#include <stdio.h>
#include <stdlib.h>
#include <strings.h>
#include <windows.h>
#include <dir.h>
#include <conio.h>
#include <time.h>

    void FuncCOLOR();
    void FuncERROR();
    void FuncHELP();
    void FuncOK();
    void FuncSETTING();
    void FuncWAIT();

int main(int argc, char *argv[]) {
    FuncHELP();
    system("cls");
    FuncOK();
    FuncERROR();
    FuncSETTING();
    FuncWAIT();      
}

void FuncERROR()
{
    FuncCOLOR(15, 0);
    printf("[");
    FuncCOLOR(12,0); 
    printf("ERREUR");
    FuncCOLOR(15, 0);
    printf("]");
    printf(">");
}

void FuncHELP()
{
    FuncCOLOR(8, 0);
    printf("Command HELP:\n");
    printf("Cooldown=Y/y=yes(safe)\n         C/c=yes(unsafe)\n         !/?=ADMIN(testmode)\n");
    printf("\nCommand=T/t=Break\n        H/h=HELP\n");
    FuncCOLOR(15, 0);
    system("pause");
}

void FuncOK()
{
    FuncCOLOR(15, 0);
    printf("[");
    FuncCOLOR(10,0);
    printf("OK");
    FuncCOLOR(15, 0);
    printf("]");
    printf(">");
}

void FuncSETTING()
{
    FuncCOLOR(15, 0);
    printf("[");
    FuncCOLOR(13,0);
    printf("SETTING");
    FuncCOLOR(15, 0);
    printf("]");
    printf(">");
}

void FuncWAIT()
{
    FuncCOLOR(15, 0);
    printf("[");
    FuncCOLOR(9,0);
    printf("WAIT");
    FuncCOLOR(15, 0);
    printf("]");
    printf(">");
}

void FuncCOLOR(int couleurDuTexte,int couleurDeFond) // fonction d'affichage de couleurs
{
    HANDLE H=GetStdHandle(STD_OUTPUT_HANDLE);
    SetConsoleTextAttribute(H,couleurDeFond*16+couleurDuTexte);
}