#include <pthread.h>
#include <stdatomic.h>
#include <assert.h>
#include <stdbool.h>

// A Critique of ANSI SQL Isolation Levels. PAGE 5

// -------- Threads
volatile int x;
volatile int y;

void *T1(void *vargp)
{
    atomic_thread_fence(memory_order_acquire);
    x=1;
    y=1;
    atomic_thread_fence(memory_order_release);
    __VERIFIER_assert(x==1 && y==1); 
}

void *T2(void *vargp)
{
    atomic_thread_fence(memory_order_acquire);
    x=2;
    y=2;
    atomic_thread_fence(memory_order_release);  
    __VERIFIER_assert(x==2 && y==2);  
}

int main()
{  
    //printf("Create Write Thread");
    pthread_t wthread_id;
    pthread_create(&wthread_id, NULL, T1, NULL);

    //printf("Create Read Thread ");
    pthread_t rthread_id;
    pthread_create(&rthread_id, NULL, T2, NULL);

}