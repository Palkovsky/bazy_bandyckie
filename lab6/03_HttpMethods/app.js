// File 03_HttpMethods/app.js
// 
const express = require("express");
const app = express();

function printReqSummary(request) {
  console.log(`Handling ${request.method} ${request.originalUrl}`);
}

/* Store items collection in this array */
let items = [];

/* GET / -- Show main page */
app.get("/", function(request, response) {
  printReqSummary(request);
  response.send(
    `<h1>HTTP Methods</h1><ul>
      <li>Show items (GET /item)</li>
      <li>Add an item (PUT /item/:name)</li>
      <li>Remove an item (DELETE /item/:name)</li></ul>`
  );
});

/* GET /item -- Show all items from the collection */
app.get("/item", function(request, response) {
  printReqSummary(request);
  response.send(`<p>Available items: ${items.toString()}</p>`);
});

/* PUT /item/:name -- add (put) new item to the collection */
// app.put("/item/:name", function(request, response) {
//   printReqSummary(request);
//   const itemName = request.params.name;
//   /* Is the item in collection? */
//   if (items.includes(itemName)) {
//     response.send(`<p>Item "${itemName}" already in collection</p>`);
//   } else {
//     items.push(itemName);
//     response.send(`<p>Item "${itemName}" added successfully</p>`);
//  }
// });

/* PUT /item/:old_name -- updates item with old_name with name */
app.put("/item/:old_name", function(request, response) {
    printReqSummary(request);

    let oldName = request.params.old_name,
        newName = request.query.name,
        itemIndex = items.indexOf(oldName);

    if(newName === null || newName === undefined) {
        response.send("<p>Missing 'name' parameter.</p>");
        return;
    }

    if(itemIndex === -1) {
        response.send(`<p>Item "${oldName}" doesn't exists.</p>`);
        return;
    }

    if(items.includes(newName)) {
        response.send(`<p>Name "${newName}" is taken.</p>`);
        return;
    }

    items[itemIndex] = newName;
    response.send(`<p>Item "${oldName}" changed name to "${newName}."</p>`);
});

/* POST /item -- add new item to the collection */
app.post("/item", function(request, response) {
    printReqSummary(request);

    let itemName = request.query.name;

    if(itemName === null || itemName === undefined) {
        response.send("<p>Missing 'name' parameter.</p>");
        return;
    }

    /* Is the item in collection? */
    if (items.includes(itemName)) {
        response.send(`<p>Item "${itemName}" already in collection</p>`);
    } else {
        items.push(itemName);
        response.send(`<p>Item "${itemName}" added successfully</p>`);
    }
});

/* DELETE /item/:name -- remove a given item from the collection */
app.delete("/item/:name", function(request, response) {
  printReqSummary(request);
  const itemName = request.params.name;
  /* Is the item in collection? */
  if (items.includes(itemName)) {
    items = items.filter(item => item !== itemName);
    response.send(`<p>Item "${itemName}" removed successfully</p>`);
  } else {
    response.send(`<p>Item "${itemName}" doesn't exists</p>`);
  }
});

app.listen(3000);
