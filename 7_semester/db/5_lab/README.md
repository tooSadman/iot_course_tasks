### lab 5

1. Enter azure portal and go to **Resource Group** page.
2. Create Resource Group and give it a name (e.g. iot-resource-group ).
3. Next go to **Azure Cache for Redis** and create a new instance. While creating choose previously created Resource Group and give a dns name (e.g. iot-lab). Instance will be creating for some time, thus we can go to the next step.
4. Go to **Event Hubs** and create a new namespace. Chose a name for the workspace (e.g. iot-db-lab).
5. Go to new workspace and create there a new Event Hub instance. Give it a name (e.g. iot-db-eventhub).
6. Then go to the menu of newly created Event Hub entity and click on **Shared access policies**. After that create a new policy that should Listen and give it a name (e.g. iot-eh-listen).
7. Next we need to make some changes in ```./config/jsonServer.toml``` file with values from Redis and EventHub instances.
8. Start the program with running ```make```. Then open Postman and make POST calls to localhost:9000/url with body:
    ```
    {
        "url": "https://www.dallasopendata.com/resource/nr4f-efb3.json",
        "strategy": "eventHub"
    }
    ```
    Strategy could be changed also to ```redis```.
9. Then you should see new messages in EventHub entity's Process Data view or in Redis console.
