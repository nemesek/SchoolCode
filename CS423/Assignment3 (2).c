//===============================================
/*
 * Terminal interaction string processing
 * is shown in this third example.
 */
#include <stdio.h>  // Defines printf() and other fxxxxx() I/O services
#include <stdlib.h> // Defines exit() and other library support routines
#include <unistd.h> // Defines exec() family of services
#include <ctype.h>  // Defines isxxxx() family of services
#include <string.h> // Defines strxxx() family of services

#define TRUE  1
#define FALSE 0

char command[255];

main(int argc, char *argv[], char *envp[])
{
  register char **p;

  int count, pid, status, arrayIndex;
  char *cp, *prog, *cmdptr, *arg1, *arg2, *pch;

  //if(fork() == FALSE)           // Child code - Concise version: if (!fork())
  if(!fork())
  { 
    printf("Yes master?\n");
    // fgets() reads in the newline and stores it into the "command" buffer.
    // Also, null ('\0') character is placed after the last character in the buffer.
    gets(command);
    //printf("%s\n", command);
    count = strlen(command);
    printf("String length: %d\n", count);
    pch = strtok(command," ");
    arrayIndex = 0;
    while(pch != NULL)
    {
	//printf("%s\n", pch);
	argv[arrayIndex] = pch;
  	pch = strtok(NULL, " ");
        arrayIndex++;
    }
    argv[arrayIndex] = NULL;  
    printf("Reached end of string\n");
    printf("Ready to exec\n");
    printf("Number of args passing to execv: %d\n", arrayIndex);
    fflush(stdout);
    if(execv(argv[0], argv))
    {
      // -1 return means error
      printf("Exec service failed\n");
      fflush(stdout);
error:
      printf("Cannot find file, please input full path to desired file\n");
      fflush(stdout);
      exit(123);
    } // End of if(exec)
  } else
  {  // Parent code
    pid = wait(&status);         // Will the parent hang if the child has an error?
    printf("How child terminated = %d, Child return code = %d, PID = %d\n",
      ((status & 0xff00) >> 8), (status & 0xff), pid);
  } // End of fork
}  // End of main
//===============================================
