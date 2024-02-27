let currData = {
    harboursData: ["", "", "", ""]
};

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

        render();
    } catch (e) {
        console.log(e);
        document.querySelector("#log").innerHTML = "<i class='ti ti-bug'></i> Couldn't fetch new Data from the Backend";
    }
}

const render = () => {
    const map = document.querySelector("#interactive-map > #map");

    let harbsPairs = [];
    let shipPairs = [];

    currData.harboursData.forEach((val) => {
        harbsPairs.push([val[2], val[3]]);
    });

    currData.shipsData.forEach((val) => {
        shipPairs.push([val[2], val[3]]);
    });

    console.log(harbsPairs);

    if (currData != null) {
        for (let i = 0; i < currData.company.mapSize.height; i++) {
            for (let j = 0; j < currData.company.mapSize.width; j++) {
                const node = document.createElement("div");
                node.classList = ["cell"];
                node.style = `--x:${j + 1}; --y:${i + 1};`;

                if (shipPairs.some((a) => a[0] === j + "" && a[1] === i + "")) {
                    node.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-ship" viewBox="0 0 24 24" stroke-width="1.5" stroke="#2c3e50" fill="none" stroke-linecap="round" stroke-linejoin="round"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M2 20a2.4 2.4 0 0 0 2 1a2.4 2.4 0 0 0 2 -1a2.4 2.4 0 0 1 2 -1a2.4 2.4 0 0 1 2 1a2.4 2.4 0 0 0 2 1a2.4 2.4 0 0 0 2 -1a2.4 2.4 0 0 1 2 -1a2.4 2.4 0 0 1 2 1a2.4 2.4 0 0 0 2 1a2.4 2.4 0 0 0 2 -1" /><path d="M4 18l-1 -5h18l-2 4" /><path d="M5 13v-6h8l4 6" /><path d="M7 7v-4h-1" /></svg>';

                    node.classList.add("choosable");
                }

                if (harbsPairs.some((a) => a[0] === j + "" && a[1] === i + "")) {
                    node.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-anchor" viewBox="0 0 24 24" stroke-width="1.5" stroke="#2c3e50" fill="none" stroke-linecap="round" stroke-linejoin="round"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M12 9v12m-8 -8a8 8 0 0 0 16 0m1 0h-2m-14 0h-2" /><path d="M12 6m-3 0a3 3 0 1 0 6 0a3 3 0 1 0 -6 0" /></svg>';

                    node.classList.add("choosable");
                }

                map.appendChild(node);
            }
        }
    }
};

getData();

setInterval(getData, 5000);
