let currData = {};

const shipGrid = new gridjs.Grid({
    columns: ["Name", "Pos X", "Pos Y", "Direction", "Held cargo", "Current harbour"], data: []
}).render(document.querySelector("#ship-table > .table"));

const cargoGrid = new gridjs.Grid({
    columns: ["ID", "Source", "Destination"], data: []
}).render(document.querySelector("#cargo-table > .table"));

const harbourGrid = new gridjs.Grid({
    columns: ["ID", "Name", "Pos X", "Pos Y"], data: []
}).render(document.querySelector("#harbour-table > .table"));

async function getData() {
    try {
        const resp = await fetch("http://localhost:9000/data");
        const data = await resp.json();
        console.log(data);

        if (JSON.stringify(currData) != JSON.stringify(data)) {
            currData = data;

            if (!data.company) {
                document.querySelector("#company-name").innerHTML = "Company Is Null";
            } else {
                document.querySelector("#company-name").innerHTML = data.company.name;
            }

            const config = {
                search: true, pagination: {
                    limit: 10, summary: true
                }, sort: true, language: {
                    pagination: {
                        previous: "<", next: ">"
                    }
                }
            };

            shipGrid
                .updateConfig({
                    data: data.shipsData, ...config
                })
                .forceRender();

            harbourGrid
                .updateConfig({
                    data: data.harboursData, ...config
                })
                .forceRender();

            cargoGrid
                .updateConfig({
                    data: data.cargosData, ...config
                })
                .forceRender();

            document.querySelector("#log").textContent = "";
        }
    } catch (e) {
        console.log(e)
        document.querySelector("#log").innerHTML = "<i class='ti ti-bug'></i> Couldn't fetch new Data from the Backend";
    }
}

getData();

setInterval(getData, 5000);