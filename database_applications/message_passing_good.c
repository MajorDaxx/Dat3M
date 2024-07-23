#include <pthread.h>
#include <stdatomic.h>
#include <assert.h>
#include <stdbool.h>

// This test is a message poassing example
// Thread send writes a message to x and set a flag to symbolise that it can be read
// Thread receive reads the flag and then reads the message. 
// QUESTION. is it possible to read the message x without reading the flag=1

// -------- Threads
volatile int x=0;
volatile int y=0;


void *send(void *vargp)
{
     atomic_thread_fence(memory_order_acquire);
     x=1;
     y=1;
     atomic_thread_fence(memory_order_release);
}

void *receive(void *vargp)
{
    atomic_thread_fence(memory_order_acquire);
    int a = y;
    int b = x;
    __VERIFIER_assert(a==1 && b==0);
    atomic_thread_fence(memory_order_release);
    
}

int main()
{
    //printf("Create Write Thread");
    pthread_t wthread_id;
    pthread_create(&wthread_id, NULL, send, NULL);

    //printf("Create Read Thread ");
    pthread_t rthread_id;
    pthread_create(&rthread_id, NULL, receive, NULL);

    //pthread_join(rthread_id, NULL);
    //pthread_join(wthread_id, NULL);

    //exit(0);
}