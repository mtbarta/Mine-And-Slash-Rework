# Mine-And-Slash NeoForge 1.21.1 Migration Handoff

## Project Overview
**Project:** Mine-And-Slash-Rework (Minecraft mod)  
**Migration:** NeoForge 1.20.6 → NeoForge 1.21.1  
**Location:** `/Users/matt/projects/Mine-And-Slash-Rework`

## Current Status
- **Subprojects:** All compile ✅ (Library-of-Exile, ancient_obelisks, the_harvest, dungeon_realm)
- **Main mod:** 178 compilation errors remaining
- **Build command:** `./gradlew compileJava`

---

## Key API Changes Applied (Reference Patterns)

### 1. AttributeModifier UUID → ResourceLocation
**Before:**
```java
new AttributeModifier(UUID.fromString("..."), "name", value, Operation)
atri.getModifier(uuid)
atri.hasModifier(modifier)
atri.removeModifier(uuid)
```

**After:**
```java
private static final ResourceLocation MOD_ID = ResourceLocation.fromNamespaceAndPath("mns", "modifier_name");
new AttributeModifier(MOD_ID, value, Operation)  // 3 args, no name
atri.getModifier(resourceLocation)
atri.hasModifier(resourceLocation)  // Takes ResourceLocation, not AttributeModifier
atri.removeModifier(resourceLocation)
```

### 2. FMLJavaModLoadingContext Removed
**Before:**
```java
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
FMLJavaModLoadingContext.get().getModEventBus()
```

**After:**
```java
// Pass IEventBus via @Mod constructor
public MyMod(IEventBus bus) { ... }
```

### 3. LootTableSubProvider.generate() Signature
**Before:**
```java
public void generate(HolderLookup.Provider registries, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output)
```

**After:**
```java
private final HolderLookup.Provider registries;
public Provider(HolderLookup.Provider registries) { this.registries = registries; }

@Override
public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output)
```

### 4. Enchantment Holder Lookup
**Before:**
```java
EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack)
stack.enchant(Enchantments.UNBREAKING, 3)
```

**After:**
```java
var holder = level.registryAccess()
    .registryOrThrow(Registries.ENCHANTMENT)
    .getHolder(Enchantments.SILK_TOUCH)
    .orElse(null);
if (holder != null) EnchantmentHelper.getItemEnchantmentLevel(holder, stack);

// For enchanting:
var enchantHolder = player.level().registryAccess()
    .registryOrThrow(Registries.ENCHANTMENT)
    .getHolder(Enchantments.UNBREAKING)
    .orElse(null);
if (enchantHolder != null) stack.enchant(enchantHolder, 3);
```

### 5. SoundEvents Holder → .value()
**Before:**
```java
PartBuilder.playSound(SoundEvents.TRIDENT_THROW, 1D, 1D)
```

**After:**
```java
PartBuilder.playSound(SoundEvents.TRIDENT_THROW.value(), 1D, 1D)
```
Note: Some sound events like `SoundEvents.GENERIC_EXPLODE` are already `Holder<SoundEvent>`.

### 6. LootContextParams Renamed
```java
LootContextParams.KILLER_ENTITY → LootContextParams.ATTACKING_ENTITY
LootContextParams.DIRECT_KILLER_ENTITY → LootContextParams.DIRECT_ATTACKING_ENTITY
```

### 7. ModLoadingContext.registerConfig
**Before:**
```java
ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC);
```

**After:**
```java
// Now on ModContainer - commented out for now
// ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.SERVER, Config.SPEC);
```

### 8. MapRegisterBuilder.chunkGenerator
Now requires `IEventBus` as first parameter.

---

## Completed Fixes (Main Mod)

| File | Fix Applied |
|------|-------------|
| `AttributeStat.java` | UUID→ResourceLocation for modifierId |
| `HealthUtils.java` | UUID→ResourceLocation for HEARTS_MODIFIER_ID |
| `OnServerTick.java` | UUID→ResourceLocation for CASTING_SPEED_SLOW |
| `Cached.java` | Changed Pair<Attribute, UUID> to Pair<Attribute, ResourceLocation> |
| `DatapackStats.java` | UUID→ResourceLocation in AttributeStat constructor calls |
| `AttributeStatSer.java` | Updated serialization for ResourceLocation |
| `DamageEvent.java` | UUID→ResourceLocation for NO_KNOCKBACK modifier |
| `VanillaStatData.java` | UUID→ResourceLocation for applyVanillaStats/removeVanillaStats |
| `ItemNewbieGearBag.java` | Enchantment Holder registry lookup |
| `AddSpawnerExtraLootMethod.java` | Enchantment Holder registry lookup |

---

## Remaining Error Categories

### Category 1: SoundEvents `.value()` (~10+ files)
**Files:**
- `LightningSpells.java` (lines 62, 83)
- `WaterSpells.java` (line 311)
- And many more in `aoe_data/database/spells/schools/`

**Fix:** Add `.value()` to `SoundEvents.TRIDENT_THROW`, `SoundEvents.TRIDENT_THUNDER`, etc.

---

### Category 2: Enchantment Holder (~5+ files)
**Files:**
- `GiveArrowsIfNotInfinity.java:26`
- `StatCompat.java:75,80`
- `GearItemData.java:302,327` (getAllEnchantments signature)

**Fix:** Use registry lookup pattern shown above.

---

### Category 3: Rendering API Changes (~10+ files)
**Issues:**
- `renderToBuffer(PoseStack, VertexConsumer, int, int, float, float, float, float)` signature changed
- `GuiOverlays` - `Layer.render(GuiGraphics, DeltaTracker)`

**Files:**
- `ModTridentRenderer.java:34`
- `AutoSkullRender.java:49`
- `GuiOverlays.java:66-67`

**Fix:** Check new NeoForge 1.21 rendering API signatures.

---

### Category 4: Entity/Projectile API Changes
**Issues:**
- `ClientboundAddEntityPacket` constructor now requires `ServerEntity`
- `AbstractArrow` constructor signature changed
- `FollowOwnerGoal` constructor changed
- `ProjectileUtil.getMobArrow` signature changed

**Files:**
- `SimpleProjectileEntity.java:124,126,161`
- `StationaryFallingBlockEntity.java:90,92`
- `SummonEntity.java:34,71,123`

---

### Category 5: ITeleporter/Capability Removed
**Files:**
- `MapTeleporter.java:3,5` - `ITeleporter` interface removed
- `SlashCapabilities.java:5,11` - Capability system changed

**Fix:** May need to use new NeoForge dimension teleportation API.

---

### Category 6: MobEffect.addAttributeModifier
**File:** `SlashPotions.java:14`

**Fix:** Signature changed, check new API.

---

### Category 7: VanillaRarities
**File:** `VanillaRarities.java:12-20` - Multiple "cannot find symbol" errors

---

## Useful Commands

```bash
# Compile and count errors
./gradlew compileJava 2>&1 | grep -c "error:"

# Get first 30 errors  
./gradlew compileJava 2>&1 | grep "error:" | head -30

# Compile specific subproject
./gradlew :Library-of-Exile-Rework:compileJava
./gradlew :the_harvest:compileJava
```

## Reference Documentation
- [NeoForge 1.21 Primer](https://docs.neoforged.net/primer/docs/1.21/)
- Key sections: AttributeModifier, Enchantments, Registry/Tags, Rendering

## Important Notes
1. **Preserve Logic:** Update APIs without changing functionality
2. **Backwards Compatibility:** For serialized data (like VanillaStatData UUID field), keep the string field but convert to ResourceLocation at runtime
3. **IDE Warnings:** Some unused imports remain after fixes - can clean up after all errors fixed
4. **Subprojects Depend on Library-of-Exile:** Must compile first
