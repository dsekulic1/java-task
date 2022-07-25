// O(2n) => O(n)
function foo(arr) {
  let map = {}
  let minNumberThatAppearsAtLeastTwice = Number.MAX_SAFE_INTEGER

  let arrKeys = []
  for (const struct of arr) {
    arrKeys.push(struct.id)
    map[struct.value] = (map[struct.value] || 0) + 1
    if (map[struct.value] == 2)
      minNumberThatAppearsAtLeastTwice = Math.min(
        minNumberThatAppearsAtLeastTwice,
        struct.value
      )
  }
  arrKeys = findMissingNumbers(arrKeys)

  if (minNumberThatAppearsAtLeastTwice == Number.MAX_SAFE_INTEGER)
    return 'Error! There is no such value that appears at least twice in the array.'

  let resultInteger = Math.max(minNumberThatAppearsAtLeastTwice + 1, 0)
  while (map[resultInteger]) resultInteger++
  return {
    id: arrKeys.length !== 0 ? arrKeys[0] : arr.length + 1,
    value: resultInteger,
  }
}

function findMissingNumbers(arr) {
  var sparse = arr.reduce((sparse, i) => ((sparse[i] = 1), sparse), [])
  return [...sparse.keys()].filter((i) => i && !sparse[i])
}

let arr = [
  { id: 1, value: 2 },
  { id: 2, value: 2 },
  { id: 3, value: 4 },
  { id: 4, value: 5 },
  { id: 5, value: 1 },
  { id: 6, value: 6 },
  { id: 7, value: 1 },
  { id: 8, value: 2 },
  { id: 9, value: 2 },
]

console.log(foo(arr))

let arr1 = [
  { id: 1, value: 2 },
  { id: 2, value: 2 },
  { id: 3, value: 4 },
  { id: 4, value: 5 },
  { id: 5, value: 1 },
  { id: 10, value: 6 },
  { id: 7, value: 1 },
  { id: 8, value: 2 },
  { id: 11, value: 2 },
]

console.log(foo(arr1))
