# Polymorphic AE2

[![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg?&style=flat-square)](https://www.gnu.org/licenses/lgpl-3.0)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1%20%7C%2026.1.2-62B47A?style=flat-square)](https://www.minecraft.net/)
[![NeoForge](https://img.shields.io/badge/NeoForge-supported-D7742F?style=flat-square)](https://neoforged.net/)

Addon that brings [Polymorph+](https://github.com/Aminoquiz/polymorph_plus) recipe-conflict resolution to the
[Applied Energistics 2](https://github.com/AppliedEnergistics/Applied-Energistics-2) terminals.

When the ingredients in an AE2 crafting grid match more than one recipe, a selector button appears above the
result slot. Pick the output you want, exactly like the vanilla crafting table with Polymorph+ installed.

## Supported

| Minecraft | Loader | AE2 | Polymorph+ |
| --- | --- | --- | --- |
| 1.21.1 | NeoForge | 19.2.17+ | 1.2.0+ |
| 26.1.2 | NeoForge | 26.1.10-beta+ | 1.2.0+ |

AE2 ships NeoForge-only from 1.21.1 onward, so there is no Fabric build. There is no AE2 release for
1.21.8, 1.21.11 or 26.2, so those Polymorph+ versions have no addon build either.

## Covered screens

- **Crafting Terminal** (including the wireless one), conflicts resolved when the grid changes.
- **Pattern Encoding Terminal** in crafting mode. The selector is hidden in processing, smithing and
  stonecutting modes, which have no crafting-grid conflict to resolve.

## Build

```
cd <mc version>
./gradlew :neoforge:build
```

Jars land in `<mc version>/neoforge/build/libs/`. Put the matching AE2 and Polymorph+ jars in
`<mc version>/libs/` first, they are `compileOnly` and not redistributed.

## Credits and license

Structure and mixin approach are derived from [PolymorphicEnergistics](https://github.com/62832/PolymorphicEnergistics)
by 62832, which covered Polymorph on Minecraft up to 1.21.1. This project is LGPL-3.0-or-later, matching
both that mod and Polymorph+. No assets are copied from it; the selector uses the sprites shipped by Polymorph+.

Polymorph was originally written by [TheIllusiveC4](https://github.com/illusivesoulworks). Applied Energistics 2
is by the AE2 team. This addon is unofficial and not endorsed by either.
