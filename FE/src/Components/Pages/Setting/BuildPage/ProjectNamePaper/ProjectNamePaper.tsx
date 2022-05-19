import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';
import Divider from '@mui/material/Divider';
import Paper from '@mui/material/Paper';

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
        >
          <Typography variant="h5">Project Name</Typography>
        </Paper>
      </Box>
      <Paper sx={{ padding: 3, pt: 4 }}>
        <Box>
          <Typography>프로젝트 명칭</Typography>
          <TextField
            onChange={handleChange}
            fullWidth
            label="Project Name"
            variant="outlined"
            size="small"
            disabled={Boolean(projectId)}
            sx={{ my: 2 }}
            value={projectName}
          />
        </Box>
      </Paper>
    </Box>
  );
}
