## layout thrashing
layout thrashing - Is when the browser is going to recalculate something synchronously blocking the main thread. Fix: Batching first read and the write them schedule them to run in animation frame.
`offsetHeight` (a layout-dependent property)

The blower rendering pipeline
 JS -> Style -> Layout -> Paint -> Composite

The browser is lazy by design — it batches DOM writes and defers layout recalculation until it's forced to, either by the natural end of the JS task (before paint) or by you **explicitly asking for a layout-dependent value**.
Then you do `box.style.height = ...` (write), the browser marks the layout as "dirty" but doesn't recompute yet. Then on the _next_ loop iteration, you call `box.offsetHeight` (read) — since layout is dirty, the browser **cannot give you a stale/cached answer**, so it's forced to synchronously run the entire layout stage _right then, on the main thread_, just to answer your read. Then your next write dirties it again. Repeat per element = full recalculation N times instead of once.

- **`offsetHeight`**: triggers a forced synchronous layout if layout is dirty. Returns an **integer**, includes content + padding + border, but **not margin**. Rounds to nearest pixel.
- **`getBoundingClientRect()`**: also triggers forced layout if dirty. Returns a `DOMRect` with **floating-point precision** (`top`, `left`, `right`, `bottom`, `width`, `height`, `x`, `y`) — positions **relative to the viewport**, so it accounts for scroll position and transforms. Slightly more expensive than `offsetHeight` since it computes more data, but same layout-triggering behavior.
- **`getComputedStyle(el)`**: returns the **resolved/used CSS values** for every property (what's actually rendered, after cascade/inheritance/units resolved to px, etc.) as a `CSSStyleDeclaration`. Whether it triggers layout depends on **which property you read**: reading `.transform` or `.color` does _not_ trigger layout (paint/composite-only properties), but reading `.width`, `.height`, or anything position-dependent _does_ trigger a forced layout, same as the others. This is a common trick question — the answer is "it depends on the property," not a blanket yes/no.

Link:
[Avoid large, complex layouts and layout thrashing](https://web.dev/articles/avoid-large-complex-layouts-and-layout-thrashing)


## Event-loop _mechanics_
Queue: synchronous execution → microtask queue → macrotask queue![[Pasted image 20260707135043.png]]

```js
console.log('1');

setTimeout(() => console.log('2'), 0);

Promise.resolve().then(() => console.log('3'));

async function foo() {
  console.log('4');
  await null;
  console.log('5');
}

foo();

console.log('6');
```

```js
console.log('1');          // sync -> prints 1
setTimeout(...) ;          // schedules macrotask
Promise.resolve().then(...); // schedules microtask A ('3')
foo();
  console.log('4');        // sync! runs immediately when foo() is called -> prints 4
  await null;               // suspends here, schedules the rest as microtask B ('5')
console.log('6');          // sync -> prints 6
```

1 4 6 3 5 2

| Type                  | Examples                                                                                                                                                                                                                                                                              | When it runs                                                                                                  | Notes                                                                                                                    |
| --------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------ |
| **Call stack (sync)** | Top-level code, function calls, `console.log`, code before first `await` in an `async function`                                                                                                                                                                                       | Immediately, in order, blocking everything else                                                               | Runs to completion before any queue is touched. This includes the _synchronous part_ of async functions — a common trap. |
| **Microtask queue**   | `.then()` / `.catch()` / `.finally()` callbacks, async functions, Promise, queumMicrotask. code after `await` (the continuation), `queueMicrotask()`, `MutationObserver` callbacks, `process.nextTick()` (Node — technically its own even-higher-priority queue, see edge case below) | After the current synchronous run finishes, **before** the next macrotask                                     | Queue is drained **completely** — including microtasks scheduled _during_ draining — before moving to macrotasks.        |
| **Macrotask queue**   | `setTimeout()`, `setInterval()`, `setImmediate()` (Node), I/O callbacks, UI rendering/`requestAnimationFrame` (browser — its own special phase), `MessageChannel`                                                                                                                     | One macrotask is taken per event-loop "tick," then the loop drains microtasks again before the next macrotask | Only **one** macrotask runs per loop iteration, unlike microtasks which all drain at once.                               |