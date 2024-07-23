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

void *T1(void *vargp)
{
    atomic_thread_fence(memory_order_acquire);
    x=1;
    atomic_thread_fence(memory_order_seq_cst);
}

void *T2(void *vargp)
{
    atomic_thread_fence(memory_order_acquire);
    int b=x;
    __VERIFIER_assert(!(b!=1));
    atomic_thread_fence(memory_order_release);   
}

int main()
{
    //printf("Create Write Thread");
    pthread_t wthread_id;
    pthread_create(&wthread_id, NULL, T1, NULL);

    //printf("Create Read Thread ");
    pthread_t rthread_id;
    pthread_create(&rthread_id, NULL, T2, NULL);

    //exit(0);
}