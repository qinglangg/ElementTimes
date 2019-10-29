这是 Guide API 的兼容文件夹

Guide API 本无 resource，这里为自定义的。

不是不想共享使用 Patchouli 的文件夹，只是太麻烦。

不同语言文件为不同 json 文件

```json
{
  "id": "[LocationResource]",
  "author": "[Author]",
  "color": "[0xFFFFFF (以 0x/# 前缀表示16进制，否则识别为十进制), 也可以直接给出数字而非字符串，或 Color 的静态值]",
  "title": "[Title]",
  "categories": [
    {
      "name": "[Category Name]",
      "icon": "[Icon Item/Block RegistryName]",
      "entries": [
        {
          "id": "[LocationResource]",
          "name": "[Entry Name]",
          "icon": "[Icon Item/Block RegistryName]",
          "pages": [
            {
              "type": "text",
              "_comment": "text 与 key 二选一，key 为语言文件 key",
              "text": "[Text]",
              "key": "[语言文件 key]",
              "offsetY": 0
            },
            {
              "type": "image",
              "image": "[Image ResourceLocation]"
            },
            {
              "type": "text&image",
              "_comment": "text 与 key 二选一，key 为语言文件 key",
              "key": "[Text]",
              "text": "[Text]",
              "image": "[Image ResourceLocation]",
              "top": "[draw at top (text/image, 默认 text)]"
            },
            {
              "type": "recipe",
              "_comment": "id 与 recipe 二选一，render 可忽略",
              "_comment2": "render 构造可以无参，可以接受一个 IRecipe，或者接收一个 JsonObject",
              "id": "[Recipe ResourceLocation]",
              "recipe": { "_comment": "合法 recipe 的 json 格式" },
              "render": "[IRecipeRenderer 类全类名]"
            },
            {
              "type": "smelt",
              "_comment": "item ore 二选一",
              "item": "[Item/Block RegistryName, 或 OreName]",
              "ore": "[OreName。使用 item 虽然也能用 oreName，但那只显示一个]"
            },
            {
              "type": "brewing",
              "_comment": "input, ingredient 只要存在一个即可。output 不会参与 filter",
              "_comment2": "若三者都有，则默认不会验证配方是否真实可行，除非 check=true",
              "_comment3": "三者都有，且 check=true，效果与只有 input 和 ingredient 等同",
              "input": "[Item/Block RegistryName, 或 OreName，上方输入]",
              "ingredient": "[Item/Block RegistryName, 或 OreName，下方三个药水瓶槽的输入]",
              "output": "[Item/Block RegistryName, 或 OreName，下方三个药水瓶槽的输出]"
            },
            {
              "type": "sound",
              "sound": "[SoundEvent ResourceLocation]",
              "page": { "_comment": "任意一种 page 类型" }
            },
            {
              "type": "custom",
              "class": "[IPage 类全类名。IPage 构造可以无参或者接收一个 JsonObject 对象]"
            },
            {
              "type": "function",
              "class": "[方法所在类全类名]",
              "name": "[静态方法名，该方法接收一个 JsonObject 参数或无参数，返回 IPage，IPage[] 或 Collection<IPage> 对象]"
            },
            {
              "type": "field",
              "class": "[变量所在类全类名]",
              "name": "[静态变量名，该变量为 IPage，IPage[] 或 Collection<IPage> 对象]"
            }
          ]
        }
      ]
    }
  ]
}
```