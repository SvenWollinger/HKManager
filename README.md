# HKManager
The Hollow Knight savegame manager

### Features
- Name your savegames
- Move them from storage to the game and back!
- Automatic backups

### How to use it?
Just launch the java executable, as long as you have Hollow Knight installed you will see your loaded savegames on the right.

You can now move them to and from your storage.

IMPORTANT: Hollow Knight should not be running while you do this, as the game does NOT refresh the savegames unless you restart.
It wont break anything, but its not a good experience.
Steam Cloud Service might also interfere with HKManager.

### What if i want to have steam cloud enabled?
Use HKManager like this:

1. Start Hollow Knight (Steam will now sync your save files)
2. Now move your save files as you wish with HKManager
3. Once you are done, restart hollow knight (This will reload your saves ingame and sync them back to steam correctly)
4. Success!

### How does it work?
HKManager creates a folder in your user folder called .hkhome, in there you have your storage ("saves") and your backups ("backups")

When you move a savegame (eg. user1.dat) to the storage it gets renamed to user0.dat (Thats how we know its a storage savegame) and then moved in a folder with a random name.

The name of the folders in .hkmanager/saves/ doesnt matter, but when creating them we use the current unixtime + random numbers. (This is the same with the backups folder)

That means that if you want to manually insert saves into your storage you simply create a new folder and put your save files in there, renaming them to "user0" instead of for example user1.

### License

MIT License

Copyright (c) 2022 Sven Wollinger

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
