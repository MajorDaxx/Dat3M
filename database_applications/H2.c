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

void *transaction(void *vargp)
{
    atomic_thread_fence(memory_order_acquire);
     int r1x=x;
     int r1y=y;
    __VERIFIER_assert(!(r1x==50&&r1y==90));
     atomic_thread_fence(memory_order_release);
}

void *read(void *vargp)
{
    atomic_thread_fence(memory_order_acquire);
    int r2x=x;
    x=10;
    int r2y=y;
    y=90;
    
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

    //pthread_join(rthread_id, NULL);
    //pthread_join(wthread_id, NULL);

    //exit(0);
}