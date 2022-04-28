import React, { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import MDTable from 'Components/UI/Atoms/MDTable/MDTable';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import Stack from '@mui/material/Stack';
import CreateIcon from '@mui/icons-material/Create';
import { Link } from 'react-router-dom';
import { useStore } from 'Components/Store/SettingStore/SettingStore';
import BuildData from 'Components/MDClass/BuildData/BuildData';
import GitData from 'Components/MDClass/GitData/GitData';
import NginxData from 'Components/MDClass/NginxData/NginxData';

export default function Home() {
  const cleanProjecttName = useStore((state) => state.setProjectName);
  const cleanBuildConfigs = useStore((state) => state.setBuildConfigs);
  const cleanGitConfig = useStore((state) => state.setGitConfig);
  const cleanNginxConfig = useStore((state) => state.setNginxConfig);

  const handleCreateClick = () => {
    cleanProjecttName('');
    cleanBuildConfigs([new BuildData()]);
    cleanGitConfig(new GitData());
    cleanNginxConfig(new NginxData());
  };
  return (
    <Box sx={{ marginTop: 5 }}>
      <MDTable />
      <Stack mt={2} direction="row" justifyContent="flex-end" spacing={2}>
        <Link to="/setting" style={{ color: 'white', textDecoration: 'none' }}>
          <Button
            sx={{
              background: 'linear-gradient(195deg, #666, #191919)',
            }}
            variant="contained"
            endIcon={<CreateIcon />}
            onClick={handleCreateClick}
          >
            Create
          </Button>
        </Link>
      </Stack>
    </Box>
  );
}
