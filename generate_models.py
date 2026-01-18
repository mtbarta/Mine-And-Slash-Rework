import os
import json
import re

def generate_missing_models():
    log_path = 'runs/client/logs/latest.log'
    assets_dir = 'src/main/resources/assets/mmorpg'
    models_root = os.path.join(assets_dir, 'models/item')
    textures_root = os.path.join(assets_dir, 'textures/item')
    
    if not os.path.exists(log_path):
        print(f"Log file not found: {log_path}")
        return

    # Regex to find missing models
    # Example: java.io.FileNotFoundException: mmorpg:models/item/buff_potion/legendary_str.json
    pattern = re.compile(r'java\.io\.FileNotFoundException: mmorpg:models/item/(.+?)\.json')
    
    missing_models = set()
    
    with open(log_path, 'r') as f:
        for line in f:
            match = pattern.search(line)
            if match:
                missing_models.add(match.group(1)) # e.g. buff_potion/legendary_str
    
    print(f"Found {len(missing_models)} missing models in log.")
    
    generated_count = 0
    
    rarities = ['common_', 'uncommon_', 'rare_', 'epic_', 'legendary_', 'mythic_']
    
    # Directory mappings (model folder -> texture folder)
    # Based on observation: model 'food' -> texture 'meal'? 'seafood' -> 'fish'?
    folder_map = {
        'food': 'meal',
        'seafood': 'fish'
        # 'buff_potion' -> 'buff_potion' (implicit)
    }

    for model_rel_path in missing_models:
        # model_rel_path is like "buff_potion/legendary_str" or "food/rare_mana" or "weapon/staff/iron"
        
        parts = model_rel_path.split('/')
        if len(parts) > 1:
            category = parts[0]
            filename = parts[-1]
            subdir = "/".join(parts[:-1]) # e.g. "weapon/staff"
        else:
            category = ""
            filename = model_rel_path
            subdir = ""
            
        # Determine texture folder
        # Map the category (root folder) if needed, but keep the rest of the path structure
        mapped_category = folder_map.get(category, category)
        
        if subdir:
            # Replace the start of the subdir with the mapped category
            if category and subdir.startswith(category):
                texture_subdir = mapped_category + subdir[len(category):]
            else:
                texture_subdir = subdir
        else:
            texture_subdir = ""

        # Determine texture name
        # Try to strip rarity prefix
        texture_name = filename
        for r in rarities:
            if filename.startswith(r):
                texture_name = filename[len(r):]
                break
        
        # Check if this texture exists
        # Construct path: src/main/resources/assets/mmorpg/textures/item/{texture_subdir}/{texture_name}.png
        
        if texture_subdir:
            texture_fs_path = os.path.join(textures_root, texture_subdir, f"{texture_name}.png")
            texture_ref = f"mmorpg:item/{texture_subdir}/{texture_name}"
        else:
            texture_fs_path = os.path.join(textures_root, f"{texture_name}.png")
            texture_ref = f"mmorpg:item/{texture_name}"
            
        # Validate texture existence (optional, but good for correctness)
        # If mapped texture doesn't exist, maybe keep original name?
        if not os.path.exists(texture_fs_path):
            # Try original name in mapped folder
            if texture_subdir:
                 alt_path = os.path.join(textures_root, texture_subdir, f"{filename}.png")
                 if os.path.exists(alt_path):
                     texture_ref = f"mmorpg:item/{texture_subdir}/{filename}"
                     texture_fs_path = alt_path
            
            # If still not found, we might generate a placeholder or point to a default "missing" texture?
            # For now, let's generate it pointing to the inferred path even if missing, 
            # as it fixes the ModelBakery crash (it will just show purple checkerboard, which is better than crash/hang).
            # But let's verify if we can find at least one valid texture.
            pass

        # Generate Model JSON
        model_fs_path = os.path.join(models_root, f"{model_rel_path}.json")
        os.makedirs(os.path.dirname(model_fs_path), exist_ok=True)
        
        content = {
            "parent": "item/generated",
            "textures": {
                "layer0": texture_ref
            }
        }
        
        with open(model_fs_path, 'w') as f:
            json.dump(content, f, indent=4)
            
        # print(f"Generated {model_fs_path} -> {texture_ref}")
        generated_count += 1

    print(f"Generated {generated_count} missing model files.")

if __name__ == "__main__":
    generate_missing_models()
