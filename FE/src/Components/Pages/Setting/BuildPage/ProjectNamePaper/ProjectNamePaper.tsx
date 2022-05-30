import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';
import Divider from '@mui/material/Divider';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';

export default function ProjectNamePaper() {
  const projectName = useSettingStore((state) => state.projectName);
  const projectId = useSettingStore((state) => state.projectId);
  const setProjectName = useSettingStore((state) => state.setProjectName);

  const handleChange = (event: any) => {
    setProjectName(event.target.value);
  };

  return (
    <Box>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper
          sx={{
            padding: 1,
            textAlign: 'center',
            width: 200,
            color: ' white',
            background: 'linear-gradient(195deg, #666, #191919)',
          }}
          elevation={0}
        >
          <Typography variant="h5">Project Name</Typography>
        </Paper>
      </Box>
      <Paper
        sx={{ padding: 3, pt: 4, borderWidth: 3 }}
        elevation={0}
        variant="outlined"
      >
        <Grid container spacing={2}>
          <Grid item xs={2} sx={{ marginY: 'auto' }}>
            <Typography fontFamily="Noto Sans KR" fontSize={20}>
              프로젝트 명칭
            </Typography>
          </Grid>
          <Grid item xs={10}>
            <TextField
              onChange={handleChange}
              fullWidth
              label="Project Name"
              InputLabelProps={{
                sx: { color: 'rgb(200,200,200)' },
              }}
              InputProps={{ sx: { fontWeight: 'bold' } }}
              variant="outlined"
              size="small"
              disabled={Boolean(projectId)}
              sx={{ my: 2 }}
              value={projectName}
            />
          </Grid>
        </Grid>
      </Paper>
    </Box>
  );
}
