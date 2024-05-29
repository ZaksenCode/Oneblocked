# Oneblocked
Oneblocked - Easy to customize oneblock generator mod. 
Using this mod you can easily add your stages of blocks and mobs that will spawn. 
You can specify the order of stages and their length. </br>
It's recommended to use the [EMI](https://modrinth.com/mod/emi) mod, to view the block stages that the game has loaded.

If you have any questions, you can write into my [discord](https://discord.gg/3vyGkz4ZWM) channel support.

# Custom stage
Custom stage files should be putted into your datapack folder by path `data.pack_id.oneblock_stages`. </br>
Here is exmaple of stage:
```json
{
  "length": 50,
  "order": 1,
  "id": "stage_1",
  "name": "stage.oneblocked.stage_1.name",
  "block_list": [
    {
      "entry": "minecraft:grass_block",
      "chance": 25.0
    },
    {
      "entry": "minecraft:dirt",
      "chance": 25.0
    },
    {
      "entry": "minecraft:stone",
      "chance": 25.0
    },
    {
      "entry": "minecraft:oak_log",
      "chance": 25.0
    }
  ],
  "mob_list": [
    {
      "entry": "void",
      "chance": 95.0
    },
    {
      "entry": "minecraft:zombie",
      "chance": 2.5
    },
    {
      "entry": "minecraft:skeleton",
      "chance": 2.2
    }
  ]
}
```
Let's break it down. </br>
There are 6 fields in this file: `length`, `order`, `id`, `name`, `block_list` and `mob_list`. </br>
# Length
Length is the relative length of the stage. That is, how many blocks the player will have to break on this stage to get to the next one.
# Order
Order is the sequence number of the stage, for example if it is stage 1 it will probably be 1, if it is stage 2 it will probably be 2. But it can also be absolutely anything, and will be sorted from smallest to largest.
# Id
This is a unique stage identifier and should not be repeated in any other stages. I recommend naming them something like `stage_1`, `stage_2`, etc.
# Name
This is the name that will be stage, it will be displayed in the EMI, in the configurator menu and in the analyzer menu. It is recommended to name it something like `stage.addon_id.stage_id.name`.
For this name to take the format of text, you also need to create a resource pack with lang files. </br>
Example lang file, that should be putted into you resource pack lang folder:
```json
{
  "stage.oneblocked.stage_1.name": "Plains"
}
```
# Block list
This is a list of blocks that will be encountered at this stage. It must be in the form of an array (inside [ and ]), and the blocks themselves must be inside brackets { and }.
The block element contains the entry field and chance. entry is the id of the block, which must be in the format mod_id:block_id. chance is the weight of this block.
If one block has a weight of 20 and another has a weight of 180. The chances will be 10%/90%. </br>
Example block list:
```json
{
  "block_list": [
    {
      "entry": "minecraft:grass_block",
      "chance": "50"
    },
    {
      "entry": "minecraft:dirt",
      "chance": "50"
    }
  ]
}
```
# Mob list
This is the list of mobs that will be able to appear at this stage. It works the same way as the block list. Except that there is an entry called void. This is used when the block should not summon any mobs.
# A small example with 2 stages
Stage 1:
```json
{
  "length": 50,
  "order": 1,
  "id": "stage_1",
  "name": "stage.oneblocked.stage_1.name",
  "block_list": [
    {
      "entry": "minecraft:grass_block",
      "chance": 1.0
    },
    {
      "entry": "minecraft:dirt",
      "chance": 1.0
    }
  ],
  "mob_list": [
    {
      "entry": "void",
      "chance": 18.0
    },
    {
      "entry": "minecraft:zombie",
      "chance": 1.0
    },
    {
      "entry": "minecraft:skeleton",
      "chance": 1.0
    }
  ]
}
```
Stage 2:
```json
{
  "length": 100,
  "order": 2,
  "id": "stage_2",
  "name": "stage.oneblocked.stage_2.name",
  "block_list": [
    {
      "entry": "minecraft:stone",
      "chance": 1.0
    },
    {
      "entry": "minecraft:coal_ore",
      "chance": 1.0
    }
  ],
  "mob_list": [
    {
      "entry": "void",
      "chance": 16.0
    },
    {
      "entry": "minecraft:zombie",
      "chance": 2.0
    },
    {
      "entry": "minecraft:skeleton",
      "chance": 2.0
    }
  ]
}
```
Lang file:
```json
{
  "stage.oneblocked.stage_1.name": "Plains",
  "stage.oneblocked.stage_1.name": "Cave"
}
```
