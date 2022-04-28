import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { useStore } from 'Components/Store/SettingStore/SettingStore';

export default function ProjectNameBox() {
  const projectName = useStore((state) => state.projectName);
  const projectId = useStore((state) => state.projectId);
  const setProjectName = useStore((state) => state.setProjectName);

  const handleChange = (event: any) => {
    console.log(event.target.value);
    setProjectName(event.target.value);
  };

  return (
    <Box>
      <Typography>Project Name</Typography>
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
  );
}
