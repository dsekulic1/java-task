function lookup(obj, path) {
  let pathArray = path.split('.')
  for (const property of pathArray) {
    obj = obj[property]
    if (!obj) return `Error! Cannot read property: ${property}`
  }
  return obj
}

let object = {
  property1: {
    property2: 'Apple',
    property3: 'Orange',
    property4: {
      property5: 'Banana',
    },
  },
}

let path_empty = ''

let path = 'property1.property4.property5.property6'
let path1 = 'property1.property4.property5'

console.log(lookup(object, path_empty))
console.log(lookup(object, path))
console.log(lookup(object, path1))
