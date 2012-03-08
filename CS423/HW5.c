#include <stdio.h>  /* Defines printf() and other fxxxxx() I/O services */
#include <stdlib.h> /* Defines exit() and other library support routines */
#include <unistd.h> /* Defines exec() family of services */
#include <string.h>
#include <fcntl.h>
#include <sys/wait.h>

#define FALSE 0
#define TRUE 1
#define DEBUG

// To enable debugging comments use "#define DEBUG" or use "gcc -D DEBUG hw5.c"
// Example D macro invocation: D("%s", "Debug message") or D("%d %s", 5, "Debug number")
#ifdef D
#undef D
#endif
#ifdef DEBUG
#define D(format, arguments ...)   { printf(format, arguments); printf("\n"); }
#else
#define D(format, arguments ...)   // Null agument == turned off
#endif

// To enable flow control viewing use "#define FLOW" or "gcc -d FLOW hw.5"
// Example FC macro invocation: FC("%s %d", "Flow Control", 8)
// FC("string for printf with embedded % commands", arg1 [, agr2] ... [, argn])
#ifdef FC
#undef FC
#endif
#ifdef FLOW
#define FC(format, arguments ...)   { printf("File %s, Function %s:",__FILE__, __FUNCTION__); printf(format, arguments); printf("\n"); }
#else
#define FC(format, arguments ...)   // Null
#endif

//char command[255] = "/bin/ls -l > lsout";
char command[255];
char *end;

main(int argc, char *argv[], char *envp[])
{
  register char *cmdptr, **p;
  //int pid, status;
  int i, pid, pid1, pid2, status;
  //int fd1, fd3;
  int fd[3], fd0, fd1, fd2, fd3;
  int dopipe = FALSE;
  // Allocate a pipe buffer in case we need it later
  if(pipe (fd) == 0)
  {
    //D("%s %d %s %d","Pipe opened with read fd", fd[0], "and write fd", fd[1])
  }
  else
  {
    printf("Cannot open pipe\n");
    perror("Pipe allocate");
    exit(123);
  }

  //my added variables
  char *pch;
  int arrayIndex, count;
  for(p = argv; *p ; p++) printf ("%s\n", *p);

  //printf("Enter command or CTRL D to exit\n");
  while(1)
  {
	  printf("Enter command or CTRL D to exit\n");
	  dopipe = FALSE;
	  if((end = gets (command)) == 0)
	  {

		    printf("Detected CTRL D, Bye Bye\n");
			break;

	  }

		  //gets(command);
		  cmdptr = strchr (command, '|');
		  if(cmdptr == NULL)
		  {
			  //printf("Pipe is false\n");
			  dopipe = FALSE;
		  }
		  else
		  {
			  dopipe = TRUE;
		  }

		  if(dopipe == TRUE) //Do piping branch
		  {

			  	printf("Got a pipe operator, going to input to a second program\n");
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

				if((pid1 = fork()) == 0)
				{

				  if(dopipe == TRUE)
				  { //Redirect stdout to the pipe buffer
					D("%s", "Starting Child 1");
					close(1);
					if((fd1 = dup (fd[1])) >= 0)
					{
					  fprintf(stderr, "Left side program of %s: New stdout fd is %d\n", argv[0], fd1);
					} else
					{
					  fprintf(stderr, "Left side program of %s: Stdout duplicate failed\n", argv[0]);
					  perror(argv[0]);
					  exit(125);
					}
				  }
					// If required, do ">" redirection here
					// Assume string processing says that "/bin/ls -l" is to be executed
					// char command[255] = "/bin/ls -l | /usr/bin/wc";
					//command[7] = 0;
					//command[10] = 0;
					/*
					 * When the new program terminates, all write pipe descriptors will close,
					 * setting up an EOF condition in the pipe after the last byte is read
					 */
					//execl(command, command, &command[8], (char *)NULL);
					execl(command, command, argv[1], (char*)NULL);
					// Any return from new program is an error
					fprintf(stderr, "Left side program of %s: Cannot find or access %s\n", argv[0], command);
					fprintf(stderr, "Ending Child 1\n");
				  } // End of 1st child program

				 // Begin 2nd child program if pipe in command line
				  if((dopipe == TRUE) && (pid2 = fork()) == 0)
				  {

					D("%s", "Starting Child 2");
					close(0);
					if((fd0 = dup (fd[0])) >= 0)
					{
					  fprintf(stderr, "Right side program of %s: New stdin fd is %d\n", argv[0], fd0);
					} else
					{
					  fprintf(stderr, "Right side program of %s: Stdin duplicate failed\n", argv[0]);
					  perror(argv[0]);
					  exit(124);
					}
					fprintf(stderr, "At 1\n");
					// Must close our pipe write descriptor to see EOF from pipe writer
					close(fd[1]);
					fprintf(stderr, "At 2\n");
					//execl(&command[13], &command[13], (char *)NULL);
					execl(argv[3], argv[3], (char *)NULL);  //Doesn't return until parent terminates?
					fprintf(stderr, "At 3\n");
					// Any return is an error
					fprintf(stderr, "Right side program of %s: Cannot find or access %s\n", argv[0], &command[13]);
					fprintf(stderr, "Ending Child 2\n");
					fflush(stdout);
					exit(222);
				  }  // End of 2nd child program
				  // Begin parent program
				   // Must close all pipe write descriptors to see EOF from pipe writer
				   close(fd[1]);
				   // Wait for children to complete output before parent quacks
				   sleep(1);
				   fprintf(stderr, "Left side PID: %d\n", pid1);
				   //fprintf(stderr, "Right side PID: %d\n", pid2);
				   fprintf(stderr, "Looking for 1st dead child\n");
				   if((pid = wait (&status)) > 0)
				   {
					 fprintf(stderr, "How child terminated = %d, Child return code = %d, PID = %d\n",
					   ((status & 0xff00) >> 8), (status & 0xff), pid);
				   } else
				   {
					 perror("Parent");
				   }
				   fprintf(stderr, "Looking for 2nd dead child\n");
				   if((pid = wait (&status)) > 0)
				   {
					 fprintf(stderr, "How child terminated = %d, Child return code = %d, PID = %d\n",
					   ((status & 0xff00) >> 8), (status & 0xff), pid);
				   } else
				   {
					 perror("Parent");
				   }

		   }//end pipe operator code

		  else //no pipe character found
		  {
			  if(!fork())
			  {
				   // Child code
				   //printf("Yo Dude...\n");
			  	   //gets(command);
			  	   //printf("%s\n", command);
			  	  // Char routines defined in /usr/include/string.h

				    char * pstr;
				    pstr = strstr(command, "2>");  //Check for stderr redirection
					if(pstr == NULL)
					{
						cmdptr = strchr (command, '>');
			  		  	if(cmdptr == NULL)
			  		  	{
							printf("No stdout or stderr redirection \n"); //  Just execute the program normally
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
						}
			  		  	else
			  		  	{
			  			  	printf("Got a stdout redirection operator\n");
			  			  	while(*(++cmdptr) == ' ')	//Do nothing;

			  			  	printf("%s\n", cmdptr);
			  			  	// Make sure your string is null terminated
			  			  	// O_XXXX bits defined in "man 2 open"
			  			  	if((fd3 = open (cmdptr, O_RDWR | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR)) < 0)
			  			 	 {
			  				  	//Need to create file
			  				  	printf("Cannot open %s\n", cmdptr);
			  				  	goto error;
			  			  	}
			  			  	else
			  			  	{
			  				  	printf("cmdptr: %s\n", cmdptr);
			  				  	printf("Opened file \"%s\" with file descriptor %d\n", cmdptr, fd3);
			  			  	}
			  			  	close(1);
			  			  	if((fd1 = dup (fd3)) < 0)
			  			  	{
			  				  	// Stream I/O defined in /usr/include/stdio.h
			  				  	fprintf(stderr, "Duplicate failed\n");
			  				  	goto error;
			  			  	}
			  			  	else
			  			  	{
			  				  	//fprintf(stderr, "%s\n", command);
			  				  	count = strlen(command);
			  				  	//fprintf(stderr, "String length: %d\n", count);
			  				  	fprintf(stderr, "The new fd is %d\n", fd1);
			  				  	//Build array for execv
			  				  	pch = strtok(command," ");
			  				  	arrayIndex = 0;
			  				  	//Works for 2> scenario while(*pch != '2')
							  	while(*pch != '>')
			  				  	{
			  					  	//fprintf(stderr, "%s\n", pch);
			  					  	argv[arrayIndex] = pch;
			  					  	pch = strtok(NULL, " ");
			  					  	arrayIndex++;
			  				  	}
			  				  	argv[arrayIndex] = NULL;
			  				  	//fprintf(stderr, "Array Index: %d\n", arrayIndex);
			  				  	if(close (fd3) >= 0)
			  				  	{
			  						  fprintf(stderr, "%s closed\n", cmdptr);
			  				  	}
			  				  	else
			  				  	{
			  				  		  fprintf(stderr, "%s did not close\n", cmdptr);

			  				  	}
			  			  	}

			  		  	}
					}
					else
					{

						printf("Redirecting stderr to specified file arg\n");
			  			//printf("No > \n");  //Just execute the program normally
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
			  			char* fileName = argv[arrayIndex -1];
			  			printf("%s\n", fileName);
			  			//writing stderr to specified file
			  			freopen(fileName, "w", stderr);
			  			fprintf(stderr, "Adding line \n");
			  			fprintf(stderr, "Another line\n");
			  			fflush(stderr);
			  			//remove 2> and filename from argv[]
			  			arrayIndex -= 2;
			  			argv[arrayIndex] = NULL;
			  			printf("At bottom\n");

					}
					if(!dopipe)
					{
				  	    if(execv(argv[0], argv))
				  	    {
				  	      // -1 return means error
				  	      printf("In execv\n");
				  	      fflush(stdout);
				  	      printf("Exec service failed\n");
				  	      fflush(stdout);
						  error:
			        	  printf("%s: Cannot set up \"%s\" command\n", argv[0],command);
			        	  exit(123);

				  	    }
				  	    // End of if(exec)

					}

			  	  }
			  	  else
			  	  { // Parent code
			  	      pid = wait(&status);
			  	      printf("How child terminated = %d, Child return code = %d, PID = %d\n",
			  	        ((status & 0xff00) >> 8), (status & 0xff), pid);
			  	      //command == "";
			  	  } // End of if(fork)

		  }



  }

} // End of main
