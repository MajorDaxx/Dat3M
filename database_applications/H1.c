#include <pthread.h>
#include <stdatomic.h>
#include <assert.h>
#include <stdbool.h>

// Critique to ANSI SQL
// Chapter 3. Historie 1
// TODO this need to be accepted by ansi strict
// -------- Threads

volatile int x = 50;
volatile int y = 50;

volatile int r1x;
volatile int r1y;

volatile int r2x;
volatile int r2y;

void *transaction(void *vargp)
{
    atomic_thread_fence(memory_order_acquire);
     //r1x=x;
     x=10;
     //r1y=y;
     y=90;
     atomic_thread_fence(memory_order_release);
}

void *read(void *vargp)
{
    atomic_thread_fence(memory_order_acquire);
    //r2x=x;
    //r2y=y;
    __VERIFIER_assert(!(x==10 && y==50));
    atomic_thread_fence(memory_order_release);
}

int main()
{
    //printf("Create Write Thread");
    pthread_t wthread_id;
    pthread_create(wthread_id, NULL, transaction, NULL);

    //printf("Create Read Thread ");
    pthread_t rthread_id;
    pthread_create(&rthread_id, NULL, read, NULL);

    
    
    //__VERIFIER_assert(!(r2x==10&&r2y==50));

    //exit(0);
}