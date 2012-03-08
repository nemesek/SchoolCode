#include <stdio.h>  /* Defines printf() and other fxxxxx() I/O services */
#include <stdlib.h> /* Defines exit() and other library support routines */
#include <unistd.h> /* Defines exec() family of services */
#include <string.h>
#include <fcntl.h>

//char command[255] = "/bin/ls -l > lsout";
char command[255];
char *end;

main(int argc, char *argv[], char *envp[])
{
  register char *cmdptr, **p;
  int pid, status;
  int fd1, fd3;

  //my added variables 
  char *pch;
  int arrayIndex, count;
  for(p = argv; *p ; p++) printf ("%s\n", *p);

  while(1)
  {
	  printf("Enter command or CTRL D to exit\n");
	  if((end = gets (command)) == 0)
	  {
			//printf("end == %d\n", end);
		    printf("Detected CTRL D, Bye Bye\n");
			break;
	  }
	  else
	  {
		  if(!fork())
		  {		// Child code
			  	  //printf("Yo Dude...\n");
		  		  gets(command);
		  		  //printf("%s\n", command);
		  		  // Char routines defined in /usr/include/string.h
		  		  cmdptr = strchr (command, '>');
		  		  if(cmdptr == NULL)
		  		  {
		  			  //printf("No > \n");  Just execute the program normally
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
		  			  printf("Got a redirection operator\n");
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

		  	    if(execv(argv[0], argv))
		  	    {
		  	      // -1 return means error
		  	      printf("Exec service failed\n");
		  	      fflush(stdout);
				  error:
	        	  printf("%s: Cannot set up \"%s\" command\n", argv[0],command);
	        	  exit(123);

		  	    }
		  	    // End of if(exec)
		  	  }
		  	  else
		  	  { // Parent code
		  	      pid = wait(&status);
		  	      printf("How child terminated = %d, Child return code = %d, PID = %d\n",
		  	        ((status & 0xff00) >> 8), (status & 0xff), pid);
		  	  } // End of if(fork)

	  }
  }

} // End of main
