#include <pthread.h>
#include <stdatomic.h>
#include <assert.h>
#include <stdbool.h>

// Test basic support for the pthread library.
// Library symbols can be recognized by the `pthread_` prefix.
// TODO Preprocessor constants cannot be as easily recognized and could be platform-dependent.

// -------- Threads

volatile int x = 50;
volatile int y = 50;

void *transaction(void *vargp)
{
    atomic_thread_fence(memory_order_acquire);
     y=100;
     x=0;
     atomic_thread_fence(memory_order_release);
}

void *read(void *vargp)
{
    atomic_thread_fence(memory_order_acquire);
    int a = x;
    int b = y;
    atomic_thread_fence(memory_order_release);
    assert(!(a+b==100));
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