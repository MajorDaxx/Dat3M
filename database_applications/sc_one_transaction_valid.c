#include <pthread.h>
#include <stdatomic.h>
#include <assert.h>
#include <stdbool.h>


// This simple test ensures that within one transaction there is sequential concistency
// HerdingCats coWR

volatile int x = 30;

int main()
{
     atomic_thread_fence(memory_order_acquire);
     x=40;
     __VERIFIER_assert(!(x==40));
     atomic_thread_fence(memory_order_release);
}