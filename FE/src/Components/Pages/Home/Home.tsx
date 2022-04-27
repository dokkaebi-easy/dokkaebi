import React, { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import MDTable from 'Components/UI/Atoms/MDTable/MDTable';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import Stack from '@mui/material/Stack';
import CreateIcon from '@mui/icons-material/Create';
import { Link } from 'react-router-dom';
import axios from 'axios';
import ProjectDatas, {
  Project,
} from 'Components/MDClass/ProjectData/ProjectData';

interface ProjectAxios {
  projects: Project[];
}

export default function Home() {
  const [projects, setProject] = useState<Project[]>([]);

  useEffect(() => {
    axios.get('/api/project/all').then((res) => {
      const data = res.data as ProjectAxios;
      setProject(data.projects);
    });
  }, []);

  return (
    <Box sx={{ marginTop: 5 }}>
      <MDTable rows={projects} />
      <Stack mt={2} direction="row" justifyContent="flex-end" spacing={2}>
        <Button variant="outlined" startIcon={<DeleteIcon />}>
          Delete
        </Button>
        <Link to="/setting">
          <Button variant="contained" endIcon={<CreateIcon />}>
            Create
          </Button>
        </Link>
      </Stack>
    </Box>
  );
}
