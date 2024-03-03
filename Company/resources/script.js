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

            render();
        }
    } catch (e) {
        console.log(e);
        document.querySelector("#log").innerHTML = "<i class='ti ti-bug'></i> Couldn't fetch new Data from the Backend";
    }
}

const render = () => {
    const map = document.querySelector("#interactive-map > #map");

    if (currData != null) {
        for (let x = 0; x < currData.company.mapSize.width; x++) {
            for (let y = 0; y < currData.company.mapSize.height; y++) {
                const node = document.createElement("div");

                node.classList.add("cell");
                node.style = `--x:${x + 1}; --y:${y + 1};`;

                let ship;
                if (currData.shipsData.some((a) => {
                    ship = a;
                    return a[1] === x + "" && a[2] === y + "";
                })) insertShip(node, ship);

                let hrbr;
                if (currData.harboursData.some((a) => {
                    hrbr = a;
                    return a[2] === x + "" && a[3] === y + "";
                })) insertHarbour(node, hrbr);


                map.appendChild(node);
            }
        }
    }
};

const insertShip = (node, ship) => {
    node.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-ship" viewBox="0 0 24 24" stroke-width="1.5" stroke="#2c3e50" fill="none" stroke-linecap="round" stroke-linejoin="round"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M2 20a2.4 2.4 0 0 0 2 1a2.4 2.4 0 0 0 2 -1a2.4 2.4 0 0 1 2 -1a2.4 2.4 0 0 1 2 1a2.4 2.4 0 0 0 2 1a2.4 2.4 0 0 0 2 -1a2.4 2.4 0 0 1 2 -1a2.4 2.4 0 0 1 2 1a2.4 2.4 0 0 0 2 1a2.4 2.4 0 0 0 2 -1" /><path d="M4 18l-1 -5h18l-2 4" /><path d="M5 13v-6h8l4 6" /><path d="M7 7v-4h-1" /></svg>';

    node.classList.add("choosable");
    node.classList.add("with-tooltip");

    const tooltip = document.createElement("div");
    tooltip.classList.add('tooltip');

    const heldCargos = [];
    currData.cargosData.forEach(c => {
        if (ship[4] == c[0]) {
            heldCargos.push(c[0]);
        }
    })

    const cargoTxt = `<fieldset><legend>Cargo</legend>${heldCargos.map(c => `<p>${c}</p>`)}</fieldset>`;

    tooltip.innerHTML = `<h3>${ship[0]}</h3>${cargoTxt}`;

    node.appendChild(tooltip);
}

const insertHarbour = (node, hrbr) => {
    node.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-anchor" viewBox="0 0 24 24" stroke-width="1.5" stroke="#2c3e50" fill="none" stroke-linecap="round" stroke-linejoin="round"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M12 9v12m-8 -8a8 8 0 0 0 16 0m1 0h-2m-14 0h-2" /><path d="M12 6m-3 0a3 3 0 1 0 6 0a3 3 0 1 0 -6 0" /></svg>';

    node.classList.add("choosable");
    node.classList.add("with-tooltip");

    const tooltip = document.createElement("div");
    tooltip.classList.add('tooltip');

    const heldShips = [];
    currData.shipsData.forEach(s => {
        if (hrbr[1] == s[5]) {
            heldShips.push(s[0]);
        }
    })

    const heldCargos = [];
    currData.cargosData.forEach(c => {
        if (hrbr[1] == c[1]) {
            heldCargos.push(c[0]);
        }
    })

    const heldCargosAwait = [];
    currData.cargosData.forEach(c => {
        if (hrbr[1] == c[2]) {
            heldCargosAwait.push(c[0]);
        }
    })

    const shipsTxt = `<fieldset><legend>Ships</legend>${heldShips.map(s => `<p>${s}</p>`).join("")}</fieldset>`;
    const cargoTxt = `<fieldset><legend>Origin Cargos</legend>${heldCargos.map(c => `<p>${c}</p>`).join("")}</fieldset>`;
    const cargoAwaitedTxt = `<fieldset><legend>Awaited Cargos</legend>${heldCargosAwait.map(c => `<p>${c}</p>`).join("")}</fieldset>`;

    tooltip.innerHTML = `<h3>${hrbr[1]}</h3>${shipsTxt}${cargoTxt}${cargoAwaitedTxt}`;

    node.appendChild(tooltip);
}

getData();

setInterval(getData, 5000);
