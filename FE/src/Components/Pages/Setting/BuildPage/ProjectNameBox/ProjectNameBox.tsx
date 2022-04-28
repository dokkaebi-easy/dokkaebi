import React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { useStore } from 'Components/Store/SettingStore/SettingStore';

export default function ProjectNameBox() {
  const projectName = useStore((state) => state.projectName);
  const setProjectName = useStore((state) => state.setProjectName);

  const handleChange = (event: any) => {
    setProjectName(event.target.value);
  };

  return (
    <Box>
      <Typography>Project Name</Typography>
      <TextField
        onChange={handleChange}
        fullWidth
        id="outlined-basic1"
        label="Project Name"
        variant="outlined"
        size="small"
        sx={{ my: 2 }}
        placeholder="ProjectName"
        defaultValue={projectName}
      />
    </Box>
  );
}
