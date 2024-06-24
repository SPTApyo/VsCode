#ifndef HELP_H_INCLUDED
#define HELP_H_INCLUDED
void HELP(void)
{
       DEV();
       Color(7, 0);
       printf("Command HELP:\n");
       printf("Cooldown=Y/y=yes(safe)\n         C/c=yes(unsafe)\n         !/?=ADMIN(testmode)\n");
       printf("\nCommand=T/t=Break\n        H/h=HELP\n");
       Color(15, 0);
       system("pause");
}


#endif // HELP_H_INCLUDED
