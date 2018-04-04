/*
#define _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_DEPRECATE
#include <stdio.h>
#include <stdlib.h>
#include "player.cpp"

char path[] = "G:\\CloudMusic\\";
Music *musicList;
bool searchLocalMusicTest();
void main()
{
	searchLocalMusicTest();
}

bool searchLocalMusicTest()
{
	musicList = (Music*)calloc(9, sizeof(Music));
	searchLocalMusic(musicList, 9);
	return true;
}
*/