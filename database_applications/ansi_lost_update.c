#include <pthread.h>
#include <stdatomic.h>
#include <assert.h>
#include <stdbool.h>

// A Critique of ANSI SQL Isolation Levels. PAGE 6

// -------- Threads
volatile int x=0;
volatile int y=0;

volatile r1x;
volatile r2x;

void *T1(void *vargp)
{
    //increment by 30
    atomic_thread_fence(memory_order_acquire);
    int r1x =x;
    x=r1x+30;
    atomic_thread_fence(memory_order_release);
}

void *T2(void *vargp)
{   //increment by 20
    atomic_thread_fence(memory_order_acquire);
    int r2x=x;
    x=r2x + 20;
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

     __VERIFIER_assert(!(r1x==100 && r2x==100 && x==130));

}